package com.mallall.controller.backent;

import com.google.common.collect.Maps;
import com.mallall.common.Const;
import com.mallall.common.Result;
import com.mallall.common.StatusCode;
import com.mallall.pojo.Product;
import com.mallall.pojo.User;
import com.mallall.service.IFileService;
import com.mallall.service.IProductService;
import com.mallall.service.IUserService;
import com.mallall.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @program: mall
 * @author: qcc
 * @description ProductManagerController
 * @create: 2019-02-20 09:54
 * @Version: 1.0
 **/
@RestController
@RequestMapping(value = "/manager/product")
public class ProductManagerController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

    @RequestMapping(value = "save")
    @ResponseBody
    public Result productSave(HttpSession session, Product product) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");
        }
        //检查是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.saveOrUpdateProduct(product);
        } else {
            return Result.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping(value = "setStatus")
    @ResponseBody
    public Result setSaleStatus(HttpSession session, Integer productId, Integer status) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");
        }
        //检查是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.setSaleStatus(productId, status);
        } else {
            return Result.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping(value = "detail")
    @ResponseBody
    public Result getProductDetail(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");
        }
        //检查是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.getProductDetail(productId);
        } else {
            return Result.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public Result getList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");
        }
        //检查是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.getProductList(pageNum, pageSize);
        } else {
            return Result.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping(value = "search")
    @ResponseBody
    public Result productSearch(HttpSession session, String productName, Integer productId, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");
        }
        //检查是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.searchProduct(productName, productId, pageNum, pageSize);
        } else {
            return Result.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping(value = "upload")
    @ResponseBody
    public Result upload(HttpSession session, @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Result.createByErrorCodeMessage(StatusCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");
        }
        //检查是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file, path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

            Map<String, String> fileMap = Maps.newHashMap();
            fileMap.put("uri", targetFileName);
            fileMap.put("url", url);
            return Result.createBySuccess(fileMap);
        } else {
            return Result.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping(value = "richTextImgUpload")
    @ResponseBody
    public Map richTextImgUpload(HttpSession session, @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> resultMap = Maps.newHashMap();
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            resultMap.put("success",false);
            resultMap.put("msg","请登录管理员帐号");
            return resultMap;
        }
        //富文本中对于返回值有自己的要求,我们使用的是simditor所以按照simditor的要求,进行返回
        if (iUserService.checkAdminRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file, path);
            if(StringUtils.isBlank(targetFileName)){
                resultMap.put("success",false);
                resultMap.put("msg","上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
            resultMap.put("success",true);
            resultMap.put("msg","请登录管理员帐号");
            resultMap.put("file_path",url);
            response.setHeader("Access-Control-Allow-Headers","X-File-Name");
            return resultMap;
        } else {
            resultMap.put("success",false);
            resultMap.put("msg","无权限操作");
            return resultMap;
        }
    }

}
