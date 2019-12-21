package br.digivox.odravison.apiserver.repository;

import br.digivox.odravison.apiserver.domain.user.User;
import br.digivox.odravison.apiserver.shared.repository.BaseDomainRepository;

public interface UserRepository extends BaseDomainRepository<User, Long> {
}
