package com.astafievvadim.mm_backend.configuration;

import com.astafievvadim.mm_backend.model.*;
import com.astafievvadim.mm_backend.payload.FileResponse;
import com.astafievvadim.mm_backend.repo.ExhibitRepo;
import com.astafievvadim.mm_backend.service.*;
import com.astafievvadim.mm_backend.util.MockMultipartFile;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final AddressService addressService;
    private final GalleryService galleryService;
    private final HallService hallService;
    private final FileMetadataService fileMetadataService;
    private final ItemService itemService;
    private final EventService eventService;
    private final LocationService locationService;
    private final PartnerService partnerService;
    private final PartnerContributionService partnerContributionService;
    private final TicketService ticketService;
    private final TicketTypeService ticketTypeService;
    private final CityService cityService;
    private final CountryService countryService;
    private final CustomerService customerService;

    private final EmployeeService employeeService;
    private final ExhibitRepo exhibitRepo;

    public DataLoader(AddressService addressService,
                      GalleryService galleryService,
                      HallService hallService,
                      FileMetadataService fileMetadataService,
                      ItemService itemService,
                      EventService eventService,
                      LocationService locationService,
                      PartnerService partnerService,
                      PartnerContributionService partnerContributionService,
                      TicketService ticketService,
                      TicketTypeService ticketTypeService,
                      CityService cityService,
                      CountryService countryService,
                      CustomerService customerService,
                      EmployeeService employeeService,
                      ExhibitRepo exhibitRepo) {
        this.addressService = addressService;
        this.galleryService = galleryService;
        this.hallService = hallService;
        this.fileMetadataService = fileMetadataService;
        this.itemService = itemService;
        this.eventService = eventService;
        this.locationService = locationService;
        this.partnerService = partnerService;
        this.partnerContributionService = partnerContributionService;
        this.ticketService = ticketService;
        this.ticketTypeService = ticketTypeService;
        this.cityService = cityService;
        this.countryService = countryService;
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.exhibitRepo = exhibitRepo;
    }

    // Helper to create java.util.Date
    private Date createDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    @Override
    public void run(String... args) throws Exception {



        // Create Countries
        Country countryUSA = new Country();
        countryUSA.setName("USA");
        countryUSA = countryService.create(countryUSA);

        Country countryFrance = new Country();
        countryFrance.setName("France");
        countryFrance = countryService.create(countryFrance);

        // Create Cities
        City cityNY = new City();
        cityNY.setName("New York");
        cityNY.setPostalCode("10001");
        cityNY.setCountry(countryUSA);
        cityNY = cityService.create(cityNY, countryUSA.getId());

        City cityParis = new City();
        cityParis.setName("Paris");
        cityParis.setPostalCode("75000");
        cityParis.setCountry(countryFrance);
        cityParis = cityService.create(cityParis, countryFrance.getId());

        // Create Addresses
        Address address1 = new Address();
        address1.setStreet("123 Main St");
        address1.setCity(cityNY);
        address1.setCountry(countryUSA);
        address1 = addressService.create(address1);

        Address address2 = new Address();
        address2.setStreet("456 Art Blvd");
        address2.setCity(cityParis);
        address2.setCountry(countryFrance);
        address2 = addressService.create(address2);

        // Galleries
        Gallery gallery1 = new Gallery();
        gallery1.setName("Modern Art Gallery");
        gallery1.setAddress(address1);
        gallery1 = galleryService.create(gallery1);

        Gallery gallery2 = new Gallery();
        gallery2.setName("Classic Arts");
        gallery2.setAddress(address2);
        gallery2 = galleryService.create(gallery2);

        // Halls
        Hall hall1 = new Hall();
        hall1.setName("Main Hall");
        hall1.setGallery(gallery1);
        hall1 = hallService.create(hall1);

        Hall hall2 = new Hall();
        hall2.setName("Exhibit Hall");
        hall2.setGallery(gallery2);
        hall2 = hallService.create(hall2);

        // 1. Create item and save it
        Item item1 = new Item();
        item1.setName("Sunset Painting");
        item1.setDescription("A beautiful sunset painting.");
        item1.setYear(new Date(2020));
        item1.setType(ItemTypeEnum.PAINTING);
        item1 = itemService.create(item1);  // saved and managed entity returned

        // 2. Create MultipartFile mock (replace with actual bytes)
        MultipartFile multipartFile1 = new MockMultipartFile(
                "file1", "sculpture1.png", "image/png", new byte[]{ /* file bytes */ }
        );

        // 3. Add file, which internally reloads the item to attach managed entity
        FileResponse fileResponse1 = fileMetadataService.addFile(multipartFile1, item1.getId());

// Fetch FileMetadata entities by ID from fileResponse
        FileMetadata fileMetadataEntity1 = fileMetadataService.findById(fileResponse1.getFileId());

// Assign FileMetadata entities to items
        item1.setFileMetadata(fileMetadataEntity1);
        itemService.update(item1, item1.getId());

        // Events
        Event event1 = new Event();
        event1.setName("Spring Exhibition");
        event1.setStartDate(createDate(2024, 3, 1));
        event1.setEndDate(createDate(2024, 4, 30));
        event1 = eventService.create(event1);

        Event event2 = new Event();
        event2.setName("Summer Gala");
        event2.setStartDate(createDate(2024, 6, 15));
        event2.setEndDate(createDate(2024, 7, 15));
        event2 = eventService.create(event2);

        // Locations
        Location location1 = new Location();
        location1.setEvent(event1);
        location1.setItem(item1);
        location1.setHall(hall1);
        location1.setPlaceDate(createDate(2024, 3, 5));
        location1.setRemoveDate(createDate(2024, 4, 29));
        location1 = locationService.create(location1);

        Location location2 = new Location();
        location2.setEvent(event2);
        location2.setItem(item1);
        location2.setHall(hall2);
        location2.setPlaceDate(createDate(2024, 6, 16));
        location2.setRemoveDate(createDate(2024, 7, 14));
        location2 = locationService.create(location2);

        // Partners
        Partner partner1 = new Partner();
        partner1.setName("Art Lovers Inc.");
        partner1.setAddress(address1);
        partner1 = partnerService.create(partner1);

        Partner partner2 = new Partner();
        partner2.setName("Global Art Sponsors");
        partner2.setAddress(address2);
        partner2 = partnerService.create(partner2);

        // Partner Contributions
        PartnerContribution contribution1 = new PartnerContribution();
        contribution1.setPartner(partner1);
        contribution1.setGallery(gallery1);
        contribution1.setEvent(event1);
        contribution1.setStartDate(createDate(2024, 2, 20));
        contribution1.setEndDate(createDate(2024, 5, 1));
        contribution1 = partnerContributionService.create(contribution1);

        PartnerContribution contribution2 = new PartnerContribution();
        contribution2.setPartner(partner2);
        contribution2.setGallery(gallery2);
        contribution2.setEvent(event2);
        contribution2.setStartDate(createDate(2024, 5, 20));
        contribution2.setEndDate(createDate(2024, 8, 1));
        contribution2 = partnerContributionService.create(contribution2);

        // Ticket Types (use BigDecimal for price)
        TicketType ticketType1 = new TicketType();
        ticketType1.setName("Standard");
        ticketType1.setPrice(new BigDecimal("20.00"));
        ticketType1 = ticketTypeService.create(ticketType1);

        TicketType ticketType2 = new TicketType();
        ticketType2.setName("VIP");
        ticketType2.setPrice(new BigDecimal("50.00"));
        ticketType2 = ticketTypeService.create(ticketType2);

        // Create Customers
        Customer customer1 = new Customer();
        customer1.setFirstName("John");
        customer1.setLastName("Doe");
        customer1.setBirthdate(new Date());  // you can set a specific date if you want
        customer1.setEmail("john.doe@example.com");
        customer1.setTelegramUserId(123456789L);
        customer1.setTelegramUsername("john_doe");
        customer1 = customerService.create(customer1);  // assume you have customerService

        Customer customer2 = new Customer();
        customer2.setFirstName("Jane");
        customer2.setLastName("Smith");
        customer2.setBirthdate(new Date());
        customer2.setEmail("jane.smith@example.com");
        customer2.setTelegramUserId(987654321L);
        customer2.setTelegramUsername("jane_smith");
        customer2 = customerService.create(customer2);

        // Tickets
// Create Employees
        Employee employeeA = new Employee();
        employeeA.setFirstName("Employee");
        employeeA.setLastName("A");
        employeeA.setBirthdate(new Date()); // or any specific date
        employeeA.setEmail("employee.a@example.com");
// set RoleEnum if required, e.g. employeeA.setRole(roleEnum);
        employeeA.setRole(RoleEnum.ROLE_TICKET_AGENT);
        employeeA = employeeService.create(employeeA);  // assuming you have employeeService

        Employee employeeB = new Employee();
        employeeB.setFirstName("Employee");
        employeeB.setLastName("B");
        employeeB.setBirthdate(new Date());
        employeeB.setEmail("employee.b@example.com");
// set RoleEnum if required, e.g. employeeB.setRole(roleEnum);
        employeeB.setRole(RoleEnum.ROLE_ADMIN);
        employeeB = employeeService.create(employeeB);

// Tickets
        Ticket ticket1 = new Ticket();
        ticket1.setPurchaseDate(new Date());
        ticket1.setCustomer(customer1);
        ticket1.setEvent(event1);
        ticket1.setTicketType(ticketType1);
        ticket1.setEmployee(employeeA);  // set Employee entity here
        ticket1.setValid(true);
        ticket1 = ticketService.create(ticket1);

        Ticket ticket2 = new Ticket();
        ticket2.setPurchaseDate(new Date());
        ticket2.setCustomer(customer2);
        ticket2.setEvent(event2);
        ticket2.setTicketType(ticketType2);
        ticket2.setEmployee(employeeB);
        ticket2.setValid(true);
        ticket2 = ticketService.create(ticket2);

        // 1. Create a second item
        Item item2 = new Item();
        item2.setName("Ancient Sculpture");
        item2.setDescription("An old marble sculpture.");
        item2.setYear(new Date(1800));
        item2.setType(ItemTypeEnum.PAINTING);
        item2 = itemService.create(item2);

// 2. Create a MultipartFile mock for second item
        MultipartFile multipartFile2 = new MockMultipartFile(
                "file2", "sculpture2.png", "image/png", new byte[]{ /* file bytes */ }
        );
        FileResponse fileResponse2 = fileMetadataService.addFile(multipartFile2, item2.getId());
        FileMetadata fileMetadataEntity2 = fileMetadataService.findById(fileResponse2.getFileId());
        item2.setFileMetadata(fileMetadataEntity2);
        itemService.update(item2, item2.getId());

// 3. Create Exhibit
// 3. Create Exhibit
        Exhibit exhibit1 = new Exhibit();
        exhibit1.setName("Masterpieces Collection");
        exhibit1.setCreationDate(createDate(2024, 5, 1));
        exhibit1.setEndDate(createDate(2024, 10, 1));
        exhibit1.setHall(hall1);

        exhibit1.setItems(List.of(item1, item2));

        exhibitRepo.save(exhibit1);

// 5. Optionally create location for second item in a hall/event
        Location location3 = new Location();
        location3.setEvent(event1);
        location3.setItem(item2);
        location3.setHall(hall1);
        location3.setPlaceDate(createDate(2024, 3, 5));
        location3.setRemoveDate(createDate(2024, 4, 29));
        location3 = locationService.create(location3);


        System.out.println("Fake data loaded successfully.");
    }
}
