package com.example.quartz.step;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import com.example.quartz.model.ReaderReturnDTO;

/**
 * The Class FxMarketEventReader.
 *
 * @author ashraf
 */
public class ReaderImpl extends FlatFileItemReader<ReaderReturnDTO>
{
	private static final Logger log = LoggerFactory.getLogger(ReaderImpl.class);
	
    public ReaderImpl()
    {
    	log.info("[ReaderImpl] ReaderImpl()");
    	
        // Set input file
        this.setResource(new ClassPathResource("trades_source.csv"));
        
        // Skip the file header line
        this.setLinesToSkip(1);
        
        // Line is mapped to item (FxMarketEvent) using setLineMapper(LineMapper)
        this.setLineMapper(new DefaultLineMapper<ReaderReturnDTO>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    { 
                        setNames(new String[] { "stock", "time", "price", "shares" });
                    }
                });
                
                setFieldSetMapper(new BeanWrapperFieldSetMapper<ReaderReturnDTO>() {
                    {
                        setTargetType(ReaderReturnDTO.class);
                    }
                });
            }
        });
    }
}
