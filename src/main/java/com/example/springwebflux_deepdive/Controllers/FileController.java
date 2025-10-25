package com.example.springwebflux_deepdive.Controllers;

import com.example.springwebflux_deepdive.Models.FileResponse; // Import your FileResponse
import com.example.springwebflux_deepdive.Services.FileService;
import com.mongodb.client.gridfs.model.GridFSFile; // Keep for download handling logic
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.bson.Document;

import java.util.Optional;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<String>> uploadSingleFile(@RequestPart("file") FilePart filePart) {
        return fileService.uploadFile(filePart)
                .map(fileId -> ResponseEntity.ok("File uploaded with ID: " + fileId))
                .defaultIfEmpty(ResponseEntity.status(500).body("File upload failed."));
    }

    @PostMapping(value = "/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<String>> uploadMultipleFiles(@RequestPart("files") Flux<FilePart> fileParts) {
        return fileService.uploadMultipleFiles(fileParts)
                .collectList()
                .map(fileIds -> ResponseEntity.ok("Files uploaded with IDs: " + fileIds))
                .defaultIfEmpty(ResponseEntity.status(500).body("Multiple file upload failed."));
    }

    // --- MODIFIED METHOD ---
    @GetMapping
    public Flux<FileResponse> getAllFilesMetadata() { // Now returns Flux<FileResponse>
        return fileService.getAllFiles(); // Service already returns Flux<FileResponse>
    }

    @GetMapping("/download/{id}")
    public Mono<ResponseEntity<Flux<DataBuffer>>> downloadFile(@PathVariable String id) {
        return fileService.downloadFile(id)
                .flatMap(resource -> {
                    String filename = Optional.ofNullable(resource.getFilename())
                            .orElse("downloaded_file");

                    return resource.getGridFSFile()
                            .map(gridFSFile -> {
                                Document metadataDoc = gridFSFile.getMetadata();
                                String contentType = Optional.ofNullable(metadataDoc)
                                        .map(doc -> doc.getString("_contentType"))
                                        .orElse(MediaType.APPLICATION_OCTET_STREAM_VALUE);

                                return ResponseEntity.ok()
                                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                                        .contentType(MediaType.parseMediaType(contentType))
                                        .body(resource.getDownloadStream());
                            })
                            .defaultIfEmpty(ResponseEntity.notFound().build());
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteFile(@PathVariable String id) {
        return fileService.deleteFile(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}