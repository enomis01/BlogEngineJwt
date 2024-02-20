package com.example.BlogEngine.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.BlogEngine.auth.SecurityContext;
import com.example.BlogEngine.dto.UserDTO;
import com.example.BlogEngine.entities.Article;
import com.example.BlogEngine.entities.Comment;
import com.example.BlogEngine.entities.User;
import com.example.BlogEngine.exceptions.UserNotFoundException;
import com.example.BlogEngine.exceptions.UserNotMatchingException;
import com.example.BlogEngine.repositories.UserRepository;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public User createUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public User updateUser(Long id, UserDTO userDTO) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isPresent()) {
            User onDb = opt.get();
            if (SecurityContext.getCurrentUsername().equals(onDb.getEmail())
                    || SecurityContext.isCurrentUserAdmin()) {
                onDb.setUsername(userDTO.getUsername());
                onDb.setEmail(userDTO.getEmail());

                if (userDTO.getPassword() != null) {
                    // Aggiorna la password solo se è stata fornita nel DTO
                    onDb.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                }

                if (SecurityContext.isCurrentUserAdmin()) {
                    onDb.setRole(userDTO.getRole()); // Imposta il nuovo ruolo inserito nel Json.
                }

                userRepository.save(onDb);
                userDTO.setArticleIds(onDb.getArticles().stream().map(Article::getId).collect(Collectors.toList()));
                userDTO.setCommentIds(onDb.getComments().stream().map(Comment::getId).collect(Collectors.toList()));

                return onDb;
            } else {
                throw new UserNotMatchingException(
                        "L'utente che vuole modificare non corrisponde all'utente che lo ha scritto!");
            }
        } else {
            throw new UserNotFoundException("Utente da aggiornare non trovato!");
        }
    }

    public void deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (SecurityContext.getCurrentUsername().equals(user.getEmail())
                    || SecurityContext.isCurrentUserAdmin()) {
                userRepository.deleteById(id);
            } else {
                throw new UserNotMatchingException(
                        "L'utente che vuole eliminare l'utente non ha i permessi necessari!");
            }
        } else {
            throw new UserNotFoundException("Utente da eliminare non trovato!");
        }
    }

    public User getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Utente con l'username:" + username + "  non è stato trovato!"));
    }

    // Prendi tutti gli utenti e conservali in "onDbList"
    // Crea una lista vuota di utenti chiamata "toEditList"
    // Per ogni utente in "onDbList"
    // Crea una stringa "nuovaPassword" = la parte dell'email prima della chiocciola
    // + 123
    // Setta la password a passwordEncoder.encode(nuovaPassword)
    // Metti l'utente così modificato in toEditList
    // Salva tutti gli utenti in "toEditList"

    // public void migration() {

    //     List<User> onDbList = getAllUsers();
    //     List<User> toEditList = new ArrayList<>();

    //     for (User user : onDbList) {
    //         String email = user.getEmail();
    //         // Divide la parte dell'email prima della chiocciola
    //         String[] emailParts = email.split("@");
    //         if (emailParts.length > 0) {
    //             String psswPart = emailParts[0]; // La parte prima della chiocciola
    //             String nuovaPassword = psswPart + "123"; // Crea la nuova password
    //             // Imposta la nuova password codificata per l'utente
    //             user.setPassword(passwordEncoder.encode(nuovaPassword));
    //             toEditList.add(user);
    //         }
    //     }
    //     if(!toEditList.isEmpty()) userRepository.saveAll(toEditList);
    // }

}
