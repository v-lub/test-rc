package rent_cars_app.model;

import rent_cars_app.model.entitys.User;
import rent_cars_app.repository.IRentCarsRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    IRentCarsRepository rentCarRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = rentCarRepository.findUserById(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String[] roles = {"USER"};
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }
}