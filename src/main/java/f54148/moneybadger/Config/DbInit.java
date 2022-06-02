package f54148.moneybadger.Config;

import f54148.moneybadger.Entities.Currency;
import f54148.moneybadger.Entities.User;
import f54148.moneybadger.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class DbInit implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder encoder  = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        this.userRepository.deleteAll();

        User user = new User();
        user.setUsername("pesho");
        user.setPassword(encoder.encode("pesho"));
        user.setEmail("pesho@emailProvider.com");
        user.setName("Peter");
        user.setLastName("Peshev");
        user.setCurrency(Currency.EUR);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);

        List<User> users = List.of(user);

        // Save to db
        this.userRepository.saveAll(users);
    }
}
