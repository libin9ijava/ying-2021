package com.libin.controller;

import com.libin.entity.Admin;
import com.libin.service.AdminService;
import com.libin.util.ImageCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import static com.libin.util.ImageCodeUtil.createImage;

/**
 * (Admin)表控制层
 *
 * @author libin
 * @since 2021-05-25 19:14:54
 */
@Controller
@RequestMapping("admin")
public class AdminController {
	/**
	 * 服务对象
	 */
	@Resource
	private AdminService adminService;


	/**
	 * 通过主键查询单条数据
	 *
	 * @param id 主键
	 * @return 单条数据
	 */
	@GetMapping("selectOne")
	public Admin selectOne(Integer id) {
		return adminService.queryById(id);
	}

	/**
	 * 根据用户名查询用户的方法
	 *
	 * @param admin 主键
	 * @return 实例对象
	 */
	@RequestMapping("login")
	@ResponseBody
	public HashMap<String, Object> login(Admin admin, String code) {
		System.out.println("admin = " + admin);
		System.out.println("code = " + code);
		return adminService.login(admin, code);
	}

	@RequestMapping("getImageCode")
	public void getImageCode(HttpServletResponse response, HttpSession session) {
		//获得随机字符

		String securityCode = ImageCodeUtil.getSecurityCode();
		//打印随机字符
		System.out.println("打印随机字符:" + securityCode);
		//存session
		session.setAttribute("imageCode", securityCode);
		//生成图片
		BufferedImage image = createImage(securityCode);

		try (ServletOutputStream stream = response.getOutputStream()) {
			ImageIO.write(image, "png", stream);

		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	@RequestMapping("logout")
	public String logout(HttpSession session, HttpServletRequest request) {
		session.removeAttribute("admin");

		return "redirect:/login/login.jsp";
		
		if(true){}

	}

}
