package com.goldfish666.district;

import com.goldfish666.district.dto.DistrictDTO;
import com.goldfish666.district.po.District;
import com.goldfish666.district.service.DistrictService;
import com.goldfish666.district.utils.template.DistrictDTO2Entity;
import com.tuyang.beanutils.BeanCopyUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan("com.goldfish666.district.po")
@EnableJpaRepositories("com.goldfish666.district.repository")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class DistrictApplication implements CommandLineRunner {

    private final DistrictService districtService;

    public static void main(String[] args) {
        SpringApplication.run(DistrictApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("hello");
        List<DistrictDTO> districtDTO = districtService.getAddress("中国").getDistricts();
        List<District> districts = BeanCopyUtils.copyList(districtDTO, District.class, DistrictDTO2Entity.class);
        districtService.generateDistrict(districts.get(0));
    }


}
