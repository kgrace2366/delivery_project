package com.delivery_project.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CategoryRequestDto {

    @NotBlank(message = "카테고리 이름을 입력해주세요.")
    private String name;
}
