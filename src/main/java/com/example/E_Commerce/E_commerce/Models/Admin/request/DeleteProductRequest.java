package com.example.E_Commerce.E_commerce.Models.Admin.request;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
@ToString
@AllArgsConstructor
@Getter
public class DeleteProductRequest implements Serializable {
    private Long id;
}
