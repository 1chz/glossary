package app.zhc1.glossary.service;

import app.zhc1.glossary.domain.GlossaryUserDetails;
import app.zhc1.glossary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GlossaryUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(GlossaryUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: %s".formatted(username)));
    }
}
