package buld.week.u5w4bw.services;

import buld.week.u5w4bw.entities.Clients;
import buld.week.u5w4bw.entities.User;
import buld.week.u5w4bw.exceptions.NotFoundException;
import buld.week.u5w4bw.payloads.ClientUpdateDTO;
import buld.week.u5w4bw.payloads.NewClientDTO;
import buld.week.u5w4bw.repositories.ClientsDAO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    ClientsDAO clientsDAO;

    @Autowired
    Cloudinary cloudinary;

    public Page<Clients> findAll(int size, int page, String order) {
        Pageable pageable = PageRequest.of(size, page, Sort.by(order));
        return clientsDAO.findAll(pageable);
    }

    public Clients findById(UUID clientId) {
        return clientsDAO.findById(clientId).orElseThrow(() -> new NotFoundException(clientId));
    }


    public Clients clientUpdate(UUID clientId, ClientUpdateDTO body) {
        Clients updateClient = this.findById(clientId);
        updateClient.setBusinessType(body.businessType());
        updateClient.setP_IVA(body.P_IVA());
        updateClient.setEmail(body.email());
        updateClient.setRegisterDate(LocalDate.now());
        updateClient.setLastcontactDate(body.lastcontactDate());
        updateClient.setRevenue(body.revenue());
        updateClient.setPEC(body.PEC());
        updateClient.setCompanyNumber(body.companyNumber());
        updateClient.setContactMail(body.contactMail());
        updateClient.setContactName(body.contactName());
        updateClient.setContactSurname(body.contactSurname());
        updateClient.setContactNumber(body.contactNumber());
        updateClient.setBusinessLogo(body.businessLogo());

        return clientsDAO.save(updateClient);
    }

    public Clients clientSave(NewClientDTO body) {
        Clients newClient = new Clients();
        newClient.setBusinessType(body.businessType());
        newClient.setP_IVA(body.P_IVA());
        newClient.setEmail(body.email());
        newClient.setRegisterDate(LocalDate.now());
        newClient.setLastcontactDate(body.lastcontactDate());
        newClient.setRevenue(body.revenue());
        newClient.setPEC(body.PEC());
        newClient.setCompanyNumber(body.companyNumber());
        newClient.setContactMail(body.contactMail());
        newClient.setContactName(body.contactName());
        newClient.setContactSurname(body.contactSurname());
        newClient.setContactNumber(body.contactNumber());
        newClient.setBusinessLogo(body.businessLogo());

        return clientsDAO.save(newClient);
    }


    public void clientDelete(UUID clientId) {
        Clients delete = this.findById(clientId);
        clientsDAO.delete(delete);
    }

    public String uploadImage(MultipartFile file, UUID clientId) throws IOException {
        Clients found = this.findById(clientId);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setBusinessLogo(url);
        clientsDAO.save(found);
        return url;
    }

    public List<Clients> getClientsByRevenue(double revenue) {
        return clientsDAO.filterByYearlyRevenue(revenue);
    }

    public List <Clients> getClientsbyRegisterDate (LocalDate registrationDate) {
        return clientsDAO.filterByRegisterDate(registrationDate);
    }

    public List<Clients> getClientsbyLastContact(LocalDate lastcontactDate) {
        return clientsDAO.filterBylastContactDate(lastcontactDate);


    }

    public List<Clients> getClientsbyName(String contactName) {
        return clientsDAO.filterByContactName(contactName);


    }

}
