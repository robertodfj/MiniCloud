/**
 * Utility class that provides static mapping methods between the domain model
 * {@code User} and the data transfer object {@code UserDTO}.
 *
 * <p>This class is stateless and contains convenience methods to convert in both
 * directions. Implementations are null-safe: when a null input is provided, the
 * corresponding method returns null.</p>
 */
 
/**
 * Convert a {@code User} model instance to a {@code UserDTO}.
 *
 * <p>The returned DTO will contain the id, userName and email copied from the
 * given model. This method does not modify the input object.</p>
 *
 * @param user the {@code User} model to convert; may be {@code null}
 * @return a new {@code UserDTO} with fields populated from {@code user}, or
 *         {@code null} if {@code user} is {@code null}
 */
 
/**
 * Convert a {@code UserDTO} to a {@code User} model instance.
 *
 * <p>A new {@code User} object is created and its id, userName and email fields
 * are set from the provided DTO. This method does not validate the values;
 * callers should perform any necessary validation after conversion.</p>
 *
 * @param userDTO the {@code UserDTO} to convert; may be {@code null}
 * @return a new {@code User} model with fields populated from {@code userDTO},
 *         or {@code null} if {@code userDTO} is {@code null}
 */
package com.minicloud.mapper;

import com.minicloud.dto.UserDTO;
import com.minicloud.model.User;

public class UserMapper {

    // Convertir Model a DTO
    public static UserDTO toDTO(User user) {
       if (user == null) {
           return null;
       }
       return new UserDTO(
              user.getId(),
              user.getUserName(),
              user.getEmail()
       );

    }

    // Convertir DTO a Model
    public static User toModel(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }      
        User user = new User();
        user.setId(userDTO.getId());
        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}
