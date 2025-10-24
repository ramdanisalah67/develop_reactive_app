package com.example.springwebflux_deepdive.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private String id;
    private String filename;
    private String contentType;
    private long size;
    private String url;


}
