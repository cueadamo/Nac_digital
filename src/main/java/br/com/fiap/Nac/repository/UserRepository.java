package br.com.fiap.Nac.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.Nac.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
