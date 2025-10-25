package com.example.springwebflux_deepdive.Services;

import com.example.springwebflux_deepdive.Models.FileMetadata; // This is your custom metadata for upload
import com.example.springwebflux_deepdive.Models.FileResponse; // Import your FileResponse DTO
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsResource;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FileService {

    private final ReactiveGridFsTemplate gridFsTemplate;

    public FileService(ReactiveGridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }

    public Mono<String> uploadFile(FilePart filePart) {
        FileMetadata metadata = new FileMetadata("Uploaded via API", "user123");

        return gridFsTemplate.store(
                        filePart.content(),
                        filePart.filename(),
                        filePart.headers().getContentType() != null ?
                                filePart.headers().getContentType().toString() :
                                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                        metadata
                )
                .map(ObjectId::toString); // Returns the hex string ID
    }

    public Flux<String> uploadMultipleFiles(Flux<FilePart> fileParts) {
        return fileParts.flatMap(this::uploadFile);
    }

    public Mono<GridFSFile> getFile(String id) {
        return gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(new ObjectId(id))));
    }

    public Mono<ReactiveGridFsResource> downloadFile(String id) {
        return gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(new ObjectId(id))))
                .flatMap(gridFsTemplate::getResource);
    }

    /**
     * Retrieves all file metadata stored in GridFS, mapped to FileResponse DTOs.
     * @return A Flux emitting FileResponse objects.
     */
    public Flux<FileResponse> getAllFiles() { // Return Flux<FileResponse>
        return gridFsTemplate.find(new Query())
                .map(FileResponse::fromGridFSFile); // Map GridFSFile to FileResponse
    }

    public Mono<Void> deleteFile(String id) { // Corrected parameter name from String to id
        return gridFsTemplate.delete(Query.query(Criteria.where("_id").is(new ObjectId(id))));
    }
}