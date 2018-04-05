package com.github.javahao.base;

import com.github.javahao.annotation.Permission;
import com.github.javahao.annotation.Relation;
import com.github.javahao.exception.CRUDException;
import com.github.javahao.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

/**
 * usedfor：公共控制器，封装了基本的增删改查控制方法
 * 默认@Permission资源权限分为
 *      view:查询权限
 *      update:修改权限
 *      insert:插入权限
 *      delete:删除权限
 * Created by javahao on 2017/6/19.
 * auth：JavaHao
 */
public abstract class BaseController<T extends BaseBean,S extends Service> {
    protected final transient Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取service实例
     * @return 结果
     */
    protected abstract S getService();

    protected final String MSG = "msg";
    /**
     * view层包路径
     * @return 结果
     */
    protected abstract String pkg();
    /**
     * 功能模块
     * @return 结果
     */
    protected abstract String model();

    /**
     * url默认model的处理方式，转为小写
     * @return model的处理结果
     */
    protected String modelProcess(){
        return model().toLowerCase();
    }

    /**
     * 功能模块描述（功能操作提示信息）
     * @return 结果
     */
    protected abstract String modelMsg();

    /**
     * 跳转修改或者新增页面加载的公共数据，如果需要额外加入关联表数据可重写此方法
     * @param model model
     */
    protected void updateCommon(Model model,T t) throws CRUDException {

    }

    /**
     * 跳转list页面加载的公共数据
     * @param model model
     */
    protected void listCommon(Model model,T t)throws CRUDException {}

    /**
     * 查询所有数据控制方法
     * @param model model
     * @param t
     * @return
     * @throws CRUDException
     */
    @Permission(value = "view")
    @RequestMapping(value = {"/list",""})
    public String list(Model model,T t) throws CRUDException {
        model.addAttribute("list",getService().list(t));
        listCommon(model,t);
        return pkg()+"/list";
    }

    /**
     * 按照分页查询数据
     * @param model model
     * @param t
     * @return
     * @throws CRUDException
     */
    @Permission(value = "view")
    @RequestMapping("/page")
    public String page(Model model, T t) throws CRUDException {
        model.addAttribute("page",getService().page(t));
        listCommon(model,t);
        return pkg()+"/list";
    }

    /**
     * 按照分页查询json数据
     * @param t
     * @return
     * @throws CRUDException
     */
    @Permission(value = "view")
    @RequestMapping("/page/json")
    @ResponseBody
    public Page page(T t) throws CRUDException {
        listCommon(null,t);
        return getService().page(t);
    }

    /**
     * 修改/新增跳转页面初始方法
     * @param model model
     * @param t
     * @return
     * @throws CRUDException
     */
    @Permission(value = {"update","create","add","insert"},relation = Relation.OR)
    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String edit(Model model,T t) throws CRUDException {
        if(null!=t.getId())
            model.addAttribute(model(),getService().one(t));
        updateCommon(model,t);
        return pkg()+"/edit";
    }
    /**
     * 保存/修改数据
     * @param t
     * @return
     * @throws CRUDException
     */
    @Permission(value = {"update","create","add","insert"},relation = Relation.OR)
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    protected String edit(T t, RedirectAttributes redirectAttributes)throws CRUDException{
        if(null!=t.getId()) {
            getService().update(t);
            redirectAttributes.addFlashAttribute(MSG, modelMsg()+"修改成功！");
        }else {
            getService().save(t);
            redirectAttributes.addFlashAttribute(MSG, modelMsg()+"保存成功！");
        }
        return "redirect:/"+modelProcess()+"/page";
    }

    /**
     * 删除数据
     * @param model model
     * @param t
     * @param redirectAttributes
     * @return
     * @throws CRUDException
     */
    @Permission(value = "delete")
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    protected String delete(Model model,T t,RedirectAttributes redirectAttributes) throws CRUDException {
        t.setIdList(Arrays.asList(t.getIdStr().split(",")));
        getService().delete(t);
        redirectAttributes.addFlashAttribute(MSG, modelMsg()+"删除成功！");
        return "redirect:/"+modelProcess()+"/page";
    }
}
