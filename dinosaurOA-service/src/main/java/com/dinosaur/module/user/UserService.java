package com.dinosaur.module.user;

import com.dinosaur.core.util.DateUtil;
import com.dinosaur.core.util.EncryptUtil;
import com.dinosaur.core.util.HashKit;
import com.dinosaur.module.user.dao.UserDAO;
import com.dinosaur.module.user.entity.User;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 用户service
 * @Author Alcott Hawk
 * @Date 11/21/2016
 */
@Service
@Transactional
public class UserService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDAO userDAO;

    /**
     * 通过用户名获取用户
     * @return 用户对象
     */
    public User getUserByUserNmae(String username){
        User user = userDAO.findByNickName(username);
        return user;
    }

    /**
     * 保存一个用户实体
     * @param user 用户对象
     */
    public <S extends T> User addUser(User user){
        String salt = EncryptUtil.encodeHex(EncryptUtil.salt(8));
        user.setSalt(salt);
        user.setCreateDate(DateUtil.getCurrentTime());
        SimpleHash simpleHash = new SimpleHash(HashKit.SHA1,user.getPassword(),salt,1024);
        user.setPassword(simpleHash.toString());
        user.setNickName(user.getName());
        return userDAO.save(user);
    }

    /**
     * 删除一个用户
     * @param id
     * @return
     */
    public boolean delete(String id){
        userDAO.delete(id);
        return true;
    }

    public List<User> getUserByPage(int pageSize,int pageNo){

        Sort sort = new Sort(Sort.Direction.DESC);
        Pageable page = new PageRequest(pageNo - 1,pageSize,sort);

        Specification<User> spec = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return null;
            }
        };
        return null;
    }

}
