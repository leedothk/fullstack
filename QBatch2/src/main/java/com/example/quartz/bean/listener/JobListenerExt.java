package com.example.quartz.bean.listener;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.quartz.model.BizVO;
import com.example.quartz.model.FileWriteDTO;

public class JobListenerExt extends JobExecutionListenerSupport
{
    private static final Logger log       = LoggerFactory.getLogger(JobListenerExt.class);
    private static final String HEADER    = "stock,open,close,low,high";
    private static final String LINE_DILM = ",";

    @Autowired
    private BizVO bizVO;

    @Override
    public void afterJob(JobExecution jobExecution)
    {
    	log.info("[JobListener] afterJob()");
    	
        if (jobExecution.getStatus() == BatchStatus.COMPLETED)
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyyMMddHHmmss");
            Date             date             = new Date();
            
            String createTime = simpleDateFormat.format(date);
            
            Path path = Paths.get("prices_" + createTime + ".csv");
            
            try (BufferedWriter fileWriter = Files.newBufferedWriter(path))
            {
                fileWriter.write(HEADER);
                
                fileWriter.newLine();
                
                log.info("[JobListener] afterJob() fxMarketPricesStore : " + bizVO.values().toString());
                
                for (FileWriteDTO pd : bizVO.values())
                {
                    fileWriter.write(new StringBuilder().append(pd.getStock())
                              .append(LINE_DILM).append(pd.getOpen())
                              .append(LINE_DILM).append(pd.getClose())
                              .append(LINE_DILM).append(pd.getLow())
                              .append(LINE_DILM).append(pd.getHigh()).toString());
                    
                    fileWriter.newLine();
                }
            }
            catch (Exception e)
            {
                log.error("[JobListener] afterJob() Exception : " + path.getFileName());
            }
        }
    }
}