/**
 * Repository interface for performing CRUD and query operations on User entities.
 *
 * <p>This interface extends Spring Data JPA's JpaRepository to provide standard
 * CRUD persistence methods and also declares application-specific finder and
 * existence-check methods.</p>
 *
 * <p>Method summary:
 * <ul>
 *   <li>{@code User findByUsername(String username)} – Finds a single User by their unique username.
 *       Returns the matching User, or {@code null} if no user with the given username exists.</li>
 *   <li>{@code List<User> findByEmail(String email)} – Finds all users with the given email address.
 *       Returns a list (possibly empty) of matching User instances.</li>
 *   <li>{@code boolean existsByUsername(String username)} – Returns {@code true} if a user with the
 *       specified username exists.</li>
 *   <li>{@code boolean existsByEmail(String email)} – Returns {@code true} if at least one user with the
 *       specified email exists.</li>
 * </ul>
 * </p>
 *
 * <p>Notes:
 * <ul>
 *   <li>Method names follow Spring Data naming conventions and will be implemented
 *       automatically by Spring Data at runtime.</li>
 *   <li>The repository manages {@code User} entities whose primary key type is {@code Long}.</li>
 * </ul>
 * </p>
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
package com.minicloud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.minicloud.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

    User findByUserName(String userName);
    Optional<User> findByEmail(String email);
    User getUserById(Long id);

    //Comprobar autenticado
    @Query("SELECT u.authenticated FROM User u WHERE u.email = :email")
    boolean isAuthenticated(String email);

    //Comprobar si existen
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    
}
