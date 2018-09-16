package restapp;

import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger logger = LogManager.getLogger(UserDetailsServiceImp.class);


    @Autowired
    public UserDetailsServiceImp(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    /*Here we are using dummy data, you need to load user data from
     database or other third party application*/
        User user = findUserbyUsername(username);

        org.springframework.security.core.userdetails.User.UserBuilder builder = null;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
         //   builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
            builder.password(user.getPassword());
            builder.roles(user.getRoles());
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        return builder.build();
    }

   // @Override
    public User createUser(User user) {
       // User returnValue = new User();
       // ...

        // Generate secure password
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //UserEntity userEntity = new UserEntity();
      //  BeanUtils.copyProperties(userDto, userEntity);
        // Record data into a database
       // userEntity = userRepository.save(userEntity);

         //...
        return userRepository.save(user);
    }

    private User findUserbyUsername(String username) {
        logger.info("findUserbyUsername="+username);
        User userFound = userRepository.findByUsername(username);
        logger.info("userRepository.findByUsername(username)"+userFound);
        return userFound;

    }
}
