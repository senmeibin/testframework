package com.bpms.cmn.service;

import com.google.common.collect.Maps;
import com.bpms.cmn.dao.CityDao;
import com.bpms.cmn.entity.ext.CityExt;
import com.bpms.core.exception.ServiceValidationException;
import com.bpms.core.utils.CollectionUtils;
import com.bpms.core.utils.DateUtils;
import com.bpms.core.utils.DesUtils;
import com.bpms.core.utils.Digests;
import com.bpms.sys.dao.UserDao;
import com.bpms.sys.entity.ext.DeptExt;
import com.bpms.sys.entity.ext.UserExt;
import com.bpms.sys.service.DeptService;
import com.bpms.sys.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 城市信息服务类
 */
@Service(value = "CmnCityService")
public class CityService extends CmnBaseService<CityExt> {
    /**
     * 缓存城市名对应城市编号前缀
     */
    private static final String CACHE_PREFIX_CITY_NAME_KEY = "CACHE_CMN_CITY_NAME";

    @Autowired
    private CityDao cityDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DeptService deptService;

    @Override
    public CityDao getDao() {
        return cityDao;
    }

    /**
     * 验证前处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateBefore(CityExt entity) {

    }

    /**
     * 验证后处理[子类覆盖] 注：验证失败，直接抛出异常即可
     *
     * @param entity 实体对象
     */
    @Override
    protected void validateAfter(CityExt entity) {

    }

    /**
     * 数据保存前处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected CityExt saveBefore(CityExt entity) {
        //城市编号大写
        entity.setCityCd(entity.getCityCd().toUpperCase());

        //城市编号重复性校验
        if (this.isDuplication("uid", entity.getUid(), "cityCd", entity.getCityCd())) {
            throw new ServiceValidationException(String.format("指定的城市编号(%s)已存在，请重新输入。", entity.getCityCd()));
        }

        //城市名称重复性校验
        if (this.isDuplication("uid", entity.getUid(), "cityName", entity.getCityName())) {
            throw new ServiceValidationException(String.format("指定的城市名称(%s)已存在，请重新输入。", entity.getCityName()));
        }

        return entity;
    }

    /**
     * 数据保存后处理[子类覆盖]
     *
     * @param entity 实体对象
     */
    @Override
    protected CityExt saveAfter(CityExt entity) {
        //城市编号大写转换
        entity.setCityCd(entity.getCityCd().toUpperCase());

        DeptExt dept = this.getDataOperationDepartment(entity.getDeptUid());

        //查询用户名为cityCd + SpecialAdmin的城市专用用户是否存在
        String citySpecialAdmin = entity.getCityCd() + "SpecialAdmin";
        Map<String, Object> map = Maps.newHashMap();
        map.put("main.userCd", citySpecialAdmin);
        List<UserExt> userList = userService.search(UserExt.class, userService.getSearchSQL(null), map);

        // 如果城市专用用户不存在，则新增一个城市专用用户且不需强制修改密码
        if (CollectionUtils.isEmpty(userList)) {
            UserExt user = new UserExt();
            user.setDeptUid(dept.getUid());
            //特定用户CD
            user.setUserCd(citySpecialAdmin);
            //登录密码
            user.setPassword(Digests.shaPassword256("Awp981!Jsx"));
            //设置DES密码
            user.setDesPassword(DesUtils.encrypt("Awp981!Jsx"));
            //特定用户名
            user.setUserName(entity.getCityName() + "城市用户");
            user.setUserMail("admin@bpms.com");
            user.setUserPhone("13345678910");
            //设置不强制变更密码
            user.setForceChangePswd(0);
            //设置为系统注册
            user.setRegSystem(1);
            userDao.save(user);
        }
        else {
            UserExt user = userList.get(0);
            user.setDeptUid(dept.getUid());
            //登录密码
            user.setPassword(Digests.shaPassword256("Awp981!Jsx"));
            //设置DES密码
            user.setDesPassword(DesUtils.encrypt("Awp981!Jsx"));
            //特定用户名
            user.setUserName(entity.getCityName() + "城市用户");
            userDao.save(user);
        }

        return entity;
    }

    /**
     * 取得指定分公司下的数据运作部
     *
     * @param parentDeptUid 分公司UID
     * @return 数据运作部实体对象
     */
    private DeptExt getDataOperationDepartment(String parentDeptUid) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("main.parent_dept_uid", parentDeptUid);
        condition.put("main.dept_name", "数据运作部");

        List<DeptExt> list = deptService.search(DeptExt.class, condition);
        //数据运作部不存在的场合，自动创建数据运作部
        if (CollectionUtils.isEmpty(list)) {
            DeptExt entity = new DeptExt();
            entity.setDeptClass(3);
            entity.setDeptName("数据运作部");
            entity.setDeptAliasName("数据运作部");
            entity.setParentDeptUid(parentDeptUid);
            entity.setDispSeq(99999);
            deptService.save(entity);
            return entity;
        }
        return list.get(0);
    }

    /**
     * 数据删除前处理[子类覆盖]
     *
     * @param ids ID集合
     * @return 处理结果
     */
    @Override
    protected Boolean deleteBefore(String ids) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param ids ID集合
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(String ids) {
        return true;
    }

    /**
     * 数据删除前处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteBefore(CityExt entity) {
        return true;
    }

    /**
     * 数据删除后处理[子类覆盖]
     *
     * @param entity 实体对象
     * @return 处理结果
     */
    @Override
    protected Boolean deleteAfter(CityExt entity) {
        return true;
    }

    /**
     * 记录状态更新前处理[子类覆盖]
     *
     * @param uid          主键UID
     * @param recordStatus 记录状态
     * @return 处理结果
     */
    @Override
    protected Boolean updateRecordStatusBefore(String uid, String recordStatus) {
        return true;
    }

    /**
     * 记录状态更新后处理[子类覆盖]
     *
     * @param uid          主键UID
     * @param recordStatus 记录状态
     * @return 处理结果
     */
    @Override
    protected Boolean updateRecordStatusAfter(String uid, String recordStatus) {
        return true;
    }

    /**
     * 根据城市名称模糊匹配取得城市编号
     *
     * @param cityName 城市名称（例：上海市 浦东新区）
     * @return 城市编码（例：SH）
     */
    public String findCityNo(String cityName) {
        Date startDate = DateUtils.getNowDate();
        String cacheCityKey = CACHE_PREFIX_CITY_NAME_KEY + "_" + cityName;
        //城市编码
        String cityNo = redisCacheManager.get(String.class, cacheCityKey);
        if (StringUtils.isNotEmpty(cityNo)) {
            return cityNo;
        }

        List<CityExt> cityList = redisCacheManager.getList(CityExt.class, CACHE_PREFIX_CITY_NAME_KEY);
        if (CollectionUtils.isEmpty(cityList)) {
            cityList = this.search(CityExt.class, this.getSearchSQL(null));
            redisCacheManager.set(CACHE_PREFIX_CITY_NAME_KEY, cityList);
        }

        for (CityExt city : cityList) {
            if (StringUtils.isNotEmpty(city.getCityName()) && StringUtils.isNotEmpty(city.getCityCd())) {
                //判断城市信息是否能匹配到XXX系统的城市：对方可能传过来cityName="上海市 浦东新区"，我们这边是cityName="上海"
                if (cityName.contains(city.getCityName())) {
                    //找到匹配城市
                    cityNo = city.getCityCd();
                    redisCacheManager.set(cacheCityKey, cityNo);
                    break;
                }
            }
        }
        long spendTime = System.currentTimeMillis() - startDate.getTime();
        if ((spendTime) > 100) {
            log.info(String.format("查找城市CityNo耗时:%s毫秒", spendTime));
        }
        return cityNo;
    }
}