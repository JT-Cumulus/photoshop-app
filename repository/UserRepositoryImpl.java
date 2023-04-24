package repository;

import user.Users;

public class UserRepositoryImpl implements UserRepository {
    private UserDaoImpl userDaoImpl;
    
    @Override
    public Users get(Long id) {
        Users user = userDaoImpl.read(id);
        return user;
    }

    @Override
    public void add(Users user) {
        userDaoImpl.create(user);
    }
}
