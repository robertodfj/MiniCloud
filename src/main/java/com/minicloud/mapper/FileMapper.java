/**
 * Utility mapper for converting between the persistence model {@code FileMeta}
 * and the data transfer object {@code FileDTO}.
 *
 * <p>This class provides stateless, static helper methods to:
 * <ul>
 *   <li>Translate a FileMeta model to a FileDTO for use in service/API layers.</li>
 *   <li>Translate a FileDTO back to a FileMeta model for persistence or domain logic.</li>
 * </ul>
 *
 * <p>Both conversion methods handle {@code null} inputs by returning {@code null}.
 *
 * @see com.minicloud.model.FileMeta
 * @see com.minicloud.dto.FileDTO
 * @see com.minicloud.mapper.UserMapper
 */
 
/**
 * Convert a {@link com.minicloud.model.FileMeta} instance to a {@link com.minicloud.dto.FileDTO}.
 *
 * <p>The conversion copies the identifier and filename, and delegates conversion of the
 * associated user/creator to {@code UserMapper.toDTO(FileMeta#getUserCreator())}.
 *
 * @param file the FileMeta model to convert; may be {@code null}
 * @return a new {@code FileDTO} populated from the provided model, or {@code null} if {@code file} is {@code null}
 */
 
/**
 * Convert a {@link com.minicloud.dto.FileDTO} instance to a {@link com.minicloud.model.FileMeta}.
 *
 * <p>The conversion creates a new {@code FileMeta}, copies the identifier and filename,
 * and delegates conversion of the nested user DTO to {@code UserMapper.toModel(FileDTO#getUserCreator())}.
 *
 * @param fileDTO the FileDTO to convert; may be {@code null}
 * @return a new {@code FileMeta} populated from the provided DTO, or {@code null} if {@code fileDTO} is {@code null}
 */
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
