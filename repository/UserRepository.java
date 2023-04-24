package repository;
import user.*;

public interface UserRepository{
    Users get(Long id);
    void add(Users user);
    void update(Users user);
    void remove(Users user);
}