package com.example.springwebflux_deepdive.Models;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;
import org.springframework.http.MediaType;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private String id;
    private String filename;
    private String contentType;
    private long size; // Renamed from 'length' for consistency with common file attributes
    private LocalDateTime uploadDate; // Added for completeness, similar to Angular's FileMetadata
    // private String url; // We'll construct this on the frontend or dynamically if needed, not store it


    public static FileResponse fromGridFSFile(com.mongodb.client.gridfs.model.GridFSFile gridFSFile) {
        if (gridFSFile == null) {
            return null;
        }

        FileResponse response = new FileResponse();
        response.setId(gridFSFile.getObjectId().toHexString());
        response.setFilename(gridFSFile.getFilename());
        response.setSize(gridFSFile.getLength());

        Optional.ofNullable(gridFSFile.getUploadDate())
                .map(date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .ifPresent(response::setUploadDate);

        // --- CORRECTION HERE: Get content type from metadata ---
        String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE; // Default fallback
        if (gridFSFile.getMetadata() != null) {
            Document metadataDoc = gridFSFile.getMetadata();
            String storedContentType = metadataDoc.getString("_contentType"); // This is where ReactiveGridFsTemplate stores it
            if (storedContentType != null) {
                contentType = storedContentType;
            }
        }
        response.setContentType(contentType);

        return response;
    }
}