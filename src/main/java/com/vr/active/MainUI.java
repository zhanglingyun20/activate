package com.vr.active;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.vr.active.common.SysConstants;
import com.vr.active.common.SystemCache;
import com.vr.active.common.db.DBUtils;
import com.vr.active.common.util.HttpRequest;
import com.vr.active.common.util.MD5Util;
import com.vr.active.common.util.Result;
import com.vr.active.model.DeviceInfo;

/**
 * Created by Halo on 2016/8/09
 * 注册界面.
 */
public class MainUI extends JFrame {
    private JPanel contentPane;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JTextField deviceNameField;


    /**
     * Author:Halo
     * methods:
     * params:
     * Time: 2016/8/11 20:49
     * return:
     * description:default
     */
    public MainUI(){
        super();
    }

    /**
     * Author:Halo
     * methods:
     * params:
     * Time: 2016/8/09 20:47
     * return:
     * description:TODO
     */
    public MainUI(boolean isWrapper){
        if(isWrapper);
        {
            initUI();
        }
    }


    /**
     * Author:Halo
     * methods:
     * params:
     * Time: 2016/8/09 20:44
     * return:
     * description:初始化界面
     */
    private  void initUI(){
        setTitle("设备注册");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane=new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.PAGE_AXIS));
        JPanel usernamePanel=new JPanel();


        JLabel usernameLable=new JLabel("授权用户:");//用户名
        usernamePanel.add(usernameLable);

        usernameTextField=new JTextField();
        usernamePanel.add(usernameTextField);
        usernameTextField.setColumns(20);
        contentPane.add(usernamePanel);



        JPanel passwordPanel = new JPanel();
        JLabel passwordLabel = new JLabel("授权密码:");//密码
        passwordPanel.add(passwordLabel);
        passwordField = new JPasswordField();
        passwordField.setColumns(20);
        passwordPanel.add(passwordField);
        contentPane.add(passwordPanel);


        JPanel deviceNamePanel = new JPanel();
        JLabel deviceNameLabel = new JLabel("设备名称:");//密码
        deviceNameField = new JTextField();
        deviceNameField.setColumns(20);
        deviceNamePanel.add(deviceNameLabel);
        deviceNamePanel.add(deviceNameField);
        contentPane.add(deviceNamePanel);


        JPanel buttonPanel=new JPanel();
        contentPane.add(buttonPanel);

        JButton submitButton=new JButton("注  册");
        submitButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                StringBuffer warnMsg = new StringBuffer("");
                String userName = usernameTextField.getText();
                String passWord = new String(passwordField.getPassword());
                String deviceName = deviceNameField.getText();
                if("".equals(userName)) {
                    warnMsg.append("授权用户不能为空!\r\n");
                }
                if("".equals(passWord)) {
                    warnMsg.append("授权密码不能为空!\r\n");
                }
                if(!"".equals(new String(warnMsg))){
                    warnDialog(warnMsg.toString());
                    return;
                }
                String mac = SystemUtil.getSystemMac();
                if (StringUtils.isEmpty(mac)) {
                	 warnDialog("获取网络地址出错，请检查网络是否畅通");
                	 return;
				}
                DeviceInfo deviceInfo = new DeviceInfo();
                deviceInfo.setAccount(userName);
                deviceInfo.setPassword(MD5Util.MD5(passWord));
                deviceInfo.setDeviceMac(mac);
                deviceInfo.setDeviceCode(userName+"_"+mac);
                deviceInfo.setDeviceName(deviceName);

                //线上请求 如果成功 则提示成功，如果失败则重新录入
                if(requetServer(warnMsg,deviceInfo)){
                	DBUtils.deleteAll();
                	deviceInfo.setIsSync(DeviceInfo.Sync.YES.getValue());
                	if (DBUtils.insert(deviceInfo)>0) {
                		 warnDialog("授权成功!");
                		 setVisible(false);
//                		 System.exit(NORMAL);
					}else{
						warnDialog("保存信息失败!");
					}
                }else{
                    warnDialog(warnMsg.toString());
                }

            }
        });
        buttonPanel.add(submitButton);
        JButton cancelButton=new JButton("退  出");
        cancelButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                MainUI.this.dispose();
            }
        });
        buttonPanel.add(cancelButton);
        setPreferredSize(new Dimension(320, 200));
        setLocationRelativeTo(null);

        pack();// 自动调整窗体大小
        setResizable(false);
        setVisible(true);

    }

    /**
     * Author:Halo
     * methods:requetServer
     * params:
     * Time: 2016/8/09 20:44
     * return:
     * description:请求注册
     */
    private boolean requetServer(StringBuffer warnMsg,DeviceInfo deviceInfo){
        String url = bulidRequesUrl(SysConstants.ACTIVE_USER);
        if (StringUtils.isNotBlank(url)) {
            HttpRequest httpRequest = new HttpRequest();
            Map<String,Object> params =  new HashMap<String, Object>();
            params.put("token", MD5Util.createToken());
            params.put("data", JSONObject.toJSONString(deviceInfo));
            String resultStr = httpRequest.post(url, params);
            try {
                Result result = JSONObject.parseObject(resultStr, Result.class);
                if (result!=null) {
                	if (Result.Code.SUCCESS.getValue().equals(result.getCode())) {
						return true;
					}else{
						warnMsg.append(result.getMessage());
						return false;
					}
                }else
                {
                    warnMsg.append("请求超时");
                    return false;
                }

            } catch (Exception e) {
                warnMsg.append("注册异常！");
            }
        }else{
            warnMsg.append("未能注册到");
        }
        return false;
    }
    private String bulidRequesUrl(String path){
        String domain = SystemCache.getSysConfigByKey(SysConstants.SERVER_DOMAIN);
        path = SystemCache.getSysConfigByKey(path);
        return domain+path;
    }

    /**
     * Author:Halo
     * methods:warnDialog
     * params:
     * Time: 2016/8/09 20:42
     * return:
     * description:弹出信息
     */
    private void warnDialog(String warnMsg){
        JOptionPane.showMessageDialog(this, warnMsg, "提示",JOptionPane.WARNING_MESSAGE);
    }
//    public static void main(String[] args) {
//        //这个单独打成授权启动程序，只有在注册授权的时候打开就行。
//        //保证这个编号唯一
//        MainUI  main = new MainUI();
//        main.initUI();
//    }
}
