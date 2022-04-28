package com.example.newcompare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.newcompare.common.utils.ZipUntils;
import com.example.newcompare.entity.OrderLog;
import com.example.newcompare.entity.TaskGroup;
import com.example.newcompare.mapper.OrderLogMapper;
import com.example.newcompare.mapper.TaskGroupMapper;
import com.example.newcompare.service.TaskGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TaskGroupServiceImpl extends ServiceImpl<TaskGroupMapper, TaskGroup> implements TaskGroupService {

    @Autowired
    private TaskGroupMapper taskGroupMapper;

    @Autowired
    private OrderLogMapper orderLogMapper;

    @Autowired
    private TaskGroupMapper mapper;

    @Override
    public Integer create(TaskGroup taskGroup) {
        return mapper.insert(taskGroup);
    }

    @Override
    public Page<TaskGroup> getGroups(Page<TaskGroup> Page, String keyWords, String startTime, String endTime, String defaultSort) {
        return mapper.getGroups(Page, keyWords, startTime, endTime, defaultSort);
    }

    @Override
    public Page<OrderLog> getGroupById(Page<OrderLog> Page, String groupId) {
        return mapper.getGroup(Page, groupId);
    }

    @Override
    public String getWorkCodeByTaskId(Integer id) {
        return mapper.getWorkCodeById(id);
    }

    @Override
    public List<TaskGroup> getAllIdFromTask(Integer[] id) {
        QueryWrapper<TaskGroup> q1=new QueryWrapper<>();
        q1.in("id",id);
        return taskGroupMapper.selectList(q1);
    }

    @Override
    public List<OrderLog> getALlIdByTask(List<TaskGroup> list) {
        List<OrderLog> q1=new ArrayList<>();
        QueryWrapper<OrderLog> wrapper=new QueryWrapper<>();
        for(TaskGroup taskGroup:list){
            List<OrderLog> orderLogs = orderLogMapper.selectList(wrapper.eq("task_id",taskGroup.getId()));
            for (OrderLog orderLog : orderLogs) {
                if("complete".equals(orderLog.getStatus()) && ("有差异".equals(orderLog.getResult()) || "无差异".equals(orderLog.getResult()))){
                    q1.add(orderLog);
                }

            }
        }

        return q1;

    }


    @Override
    public TaskGroup selectById(Integer taskId) {
        return taskGroupMapper.selectById(taskId);
    }




    /**
     *
     * @param list 订单表集合
     * @return 数据结果
     */
    @Override
    public boolean backZip(List<OrderLog> list,String path) throws Exception {

        java.io.File file=new java.io.File(path);//?
        file.mkdirs();

        for(OrderLog orderLog:list){
            if("complete".equals(orderLog.getStatus())){
                String url = orderLog.getUrl();
                if(url!=null && !"".equals(url)){
                    HttpURLConnection conn = null;
                    InputStream inputStream = null;
                    FileOutputStream e1=null;
                    try{
                        URL url1 = new URL(" http://"+url);
                        conn = (HttpURLConnection)url1.openConnection();
                        conn.connect();
                        //得到输入流
                        inputStream = conn.getInputStream();

                        e1=new FileOutputStream(path+"/"+orderLog.getFileName());

                        byte[] bys=new byte[1024];
                        int len;
                        while((len=inputStream.read(bys))!=-1){
                            e1.write(bys,0,len);
                        }
                    }catch (Exception o){
                        o.printStackTrace();
                    }finally{
                        inputStream.close();
                        e1.close();
                        conn.disconnect();
                    }

                }else{
                    return false;
                }

            }
        }

        ZipUntils.getZip(path);

        return true;

    }
}
