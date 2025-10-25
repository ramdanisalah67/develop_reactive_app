package com.example.springwebflux_deepdive.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileMetadata {
    private String description;
    private String uploadedBy;
    // Add any other custom metadata you need
}
