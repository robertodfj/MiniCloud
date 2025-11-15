/**
 * Repository interface for managing FileMeta entities.
 *
 * Extends Spring Data JPA's JpaRepository to provide standard CRUD, paging and sorting operations
 * for FileMeta instances. Declares additional query methods to retrieve files by their creator
 * and to find a file by name scoped to a specific creator.
 *
 * Implementations are generated automatically by Spring Data at runtime.
 *
 * @see com.minicloud.model.FileMeta
 * @see org.springframework.data.jpa.repository.JpaRepository
 */

/**
 * Retrieve all FileMeta instances created by the given user.
 *
 * Returns a list of FileMeta objects whose userCreator matches the provided User.
 * If the user has no associated files, an empty list is returned.
 *
 * @param userCreator the User who created the files; must not be null
 * @return a List of FileMeta objects created by the specified user (never null)
 */

/**
 * Retrieve a FileMeta by its name that belongs to the specified user.
 *
 * Finds a single FileMeta whose name equals the provided fileName and whose userCreator
 * equals the provided User. If no matching entity is found, the method returns null.
 *
 * @param fileName the name of the file to search for; must not be null
 * @param userCreator the User who created the file; must not be null
 * @return the matching FileMeta, or null if none is found
 */
package com.minicloud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minicloud.model.FileMeta;
import com.minicloud.model.User;

public interface FileRepository extends JpaRepository<FileMeta, Long> {
    
    List<FileMeta> findByUserCreator(Optional<User> user);
    FileMeta findByFileNameAndUserCreator(String fileName, User userCreator);
    FileMeta findByFileName(String fileName);

}
