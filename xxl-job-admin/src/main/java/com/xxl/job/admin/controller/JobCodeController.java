package com.xxl.job.admin.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xxl.job.admin.core.model.ReturnT;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.dao.impl.XxlJobInfoDaoImpl;

/**
 * job code controller
 * 
 * @author xuxueli 2015-12-19 16:13:16
 */
@Controller
@RequestMapping("/jobcode")
public class JobCodeController {

    @Resource
    private XxlJobInfoDaoImpl xxlJobInfoDaoImpl;

    @RequestMapping
    public String index(Model model, String jobGroup, String jobName) {
        XxlJobInfo jobInfo = xxlJobInfoDaoImpl.load(jobGroup, jobName);
        model.addAttribute("jobInfo", jobInfo);
        return "jobcode/jobcode.index";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ReturnT<String> save(Model model, String jobGroup, String jobName, String glueSource,
                                String glueRemark) {
        // valid
        if (glueRemark == null) {
            return new ReturnT<String>(500, "请输入备注");
        }
        if (glueRemark.length() < 6 || glueRemark.length() > 100) {
            return new ReturnT<String>(500, "备注长度应该在6至100之间");
        }
        XxlJobInfo jobInfoOld = xxlJobInfoDaoImpl.load(jobGroup, jobName);
        if (jobInfoOld == null) {
            return new ReturnT<String>(500, "任务不存在");
        }

        // log old code
        //		XxlJobLogGlue xxlJobLogGlue = new XxlJobLogGlue();
        //		xxlJobLogGlue.setJobGroup(jobInfoOld.getJobGroup());
        //		xxlJobLogGlue.setJobName(jobInfoOld.getJobName());
        //		xxlJobLogGlue.setGlueSource(jobInfoOld.getGlueSource());
        //		xxlJobLogGlue.setGlueRemark(jobInfoOld.getGlueRemark());

        // init new code
        //		jobInfoOld.setGlueSource(glueSource);
        //		jobInfoOld.setGlueRemark(glueRemark);

        // update new code ,and log old code
        xxlJobInfoDaoImpl.update(jobInfoOld);
        //		if (StringUtils.isNotBlank(xxlJobLogGlue.getGlueSource()) && StringUtils.isNotBlank(xxlJobLogGlue.getGlueRemark())) {
        //			xxlJobLogGlueDao.save(xxlJobLogGlue);
        //			// remove code backup more than 30
        //			xxlJobLogGlueDao.removeOld(xxlJobLogGlue.getJobGroup(), xxlJobLogGlue.getJobName(), 3);
        //		}

        return ReturnT.SUCCESS;
    }

}
