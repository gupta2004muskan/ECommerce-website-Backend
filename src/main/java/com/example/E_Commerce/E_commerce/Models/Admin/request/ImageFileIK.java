package com.example.E_Commerce.E_commerce.Models.Admin.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageFileIK implements Serializable {
    private String fileId;
    private String filePath;
}
