package com.reservation.booking_api.config;

import com.reservation.booking_api.entity.Room;
import com.reservation.booking_api.entity.User;
import com.reservation.booking_api.enums.Role;
import com.reservation.booking_api.repository.RoomRepository;
import com.reservation.booking_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        loadUsers();
        loadRooms();
    }

    private void loadUsers() {
        if (userRepository.count() > 0) {
            log.info("Utilisateurs déjà présents — chargement ignoré");
            return;
        }

        User admin = User.builder()
                .firstName("Admin")
                .lastName("Principal")
                .email("admin@booking.com")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .build();

        User user1 = User.builder()
                .firstName("Jean")
                .lastName("Dupont")
                .email("jean.dupont@booking.com")
                .password(passwordEncoder.encode("user123"))
                .role(Role.USER)
                .build();

        User user2 = User.builder()
                .firstName("Marie")
                .lastName("Martin")
                .email("marie.martin@booking.com")
                .password(passwordEncoder.encode("user123"))
                .role(Role.USER)
                .build();

        userRepository.saveAll(List.of(admin, user1, user2));
        log.info("3 utilisateurs chargés (1 admin, 2 users)");
    }

    private void loadRooms() {
        if (roomRepository.count() > 0) {
            log.info("🏢 Salles déjà présentes — chargement ignoré");
            return;
        }

        List<Room> rooms = List.of(
                Room.builder().name("Salle Einstein").capacity(10).description("Salle de réunion principale").build(),
                Room.builder().name("Salle Newton").capacity(6).description("Petite salle de réunion").build(),
                Room.builder().name("Salle Curie").capacity(20).description("Grande salle de conférence").build(),
                Room.builder().name("Salle Tesla").capacity(4).description("Salle de brainstorming").build(),
                Room.builder().name("Salle Darwin").capacity(15).description("Salle de formation").build()
        );

        roomRepository.saveAll(rooms);
        log.info("5 salles chargées");
    }
}