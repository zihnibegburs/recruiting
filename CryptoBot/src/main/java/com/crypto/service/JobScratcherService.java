package com.crypto.service;

import com.crypto.persistance.entity.Jobs;
import com.crypto.repository.JobRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JobScratcherService {

    @Autowired
    JobRepository jobRepository;

    public void scratch(){
        String host = "https://www.crypto-careers.com";
        String url = "https://www.crypto-careers.com/jobs/search?remote=full&sort=published_at";

        try {
            // Connect to the website and get the HTML document
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.2 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36").get();

            // Select the elements you want to extract (e.g., all paragraphs)
            Elements jobList = document.getElementsByClass("jobList-intro");

            // Print the extracted data
            for (Element job : jobList) {
               String href = job.getElementsByAttribute("href").get(0).attr("href");
               String jobUrl = host + href;
               Document document2 = Jsoup.connect(jobUrl)
                        .userAgent("Chrome").get();
               String description = document2.getElementsByClass("job-body").toString();
               String title = document2.title().split("|")[0];
               Jobs payload = new Jobs();
               payload.setDescription(description);
               payload.setTitle(title);
               payload.setUrl(jobUrl);
               jobRepository.save(payload);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
