package com.example.springwebflux_deepdive.Models;



import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Document("anime")
public class Anime {

    @Id
    private String id ;

    private String name ;
    private String url ;
    private Instant createdAt ;

}
