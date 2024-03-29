package buld.week.u5w4bw.services;

import buld.week.u5w4bw.entities.Comune;
import buld.week.u5w4bw.entities.Province;
import buld.week.u5w4bw.exceptions.NotFoundException;
import buld.week.u5w4bw.repositories.ProvinceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService {


    @Autowired
    ProvinceDAO provinceDAO;


    public Page<Province> findAll(int size, int page, String order) {
        Pageable pageable = PageRequest.of(size, page, Sort.by(order));
        return provinceDAO.findAll(pageable);
    }


    public List<Province> getAllProvinces() {
        return provinceDAO.findAll();
    }

    public Province findById(long id) {
        return provinceDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Province saveProvince(Province province) {
        return provinceDAO.save(province);
    }

    public Province findByProvinceCode(int code) {
        Province found = provinceDAO.findByProvinceCode(code);
        if (found != null) {
            return found;
        } else {
            throw new NotFoundException(code);
        }
    }


    // torna una lista di comuni per ogni provincia
    public List<Comune> getComunesList(int prov_code) {
        return provinceDAO.getComuneList(prov_code);
    }
}
