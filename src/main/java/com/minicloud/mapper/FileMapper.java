package com.minicloud.mapper;

import com.minicloud.dto.FileDTO;
import com.minicloud.model.FileMeta;

public class FileMapper {

    // Convertir Model a DTO
    public static FileDTO toDTO(FileMeta file) {
        if (file == null) {
            return null;
        }
        return new FileDTO(
                file.getId(),
                file.getFileName(),
                UserMapper.toDTO(file.getUserCreator())
        );
    }

    // Convertir DTO a Model
    public static FileMeta toModel(FileDTO fileDTO) {
        if (fileDTO == null) {
            return null;
        }
        FileMeta file = new FileMeta();
        file.setId(fileDTO.getId());
        file.setFileName(fileDTO.getFileName());
        file.setUserCreator(UserMapper.toModel(fileDTO.getUserCreator()));
        return file;
    }
}
