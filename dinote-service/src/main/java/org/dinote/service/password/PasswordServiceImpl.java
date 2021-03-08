package org.dinote.service.password;

import lombok.RequiredArgsConstructor;
import org.dinote.db.salt.dao.SaltReactiveDao;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final PasswordHasher passwordHasher;
    private final SaltReactiveDao saltReactiveDao;

    @Override
    public String encode(final String rawPassword) {
        return Mono.from(saltReactiveDao.getSalt())
                .map(salt -> passwordHasher.hashWithMd5(rawPassword + salt))
                .map(passwordHasher::hash)
                .block();

    }

    @Override
    public boolean matches(final String rawPassword, final String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
