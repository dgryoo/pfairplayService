package project.pfairplay.api.controller.mysql;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.pfairplay.api.model.get.PlayGroundForGet;
import project.pfairplay.storage.mysql.model.PlayGroundEntity;
import project.pfairplay.storage.mysql.repository.PlayGroundRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PlayGroundController {

    @Autowired
    private PlayGroundRepository playGroundRepository;

    @Operation(summary = "주소로 운동장 조회")
    @GetMapping("/playGround/mainAddress")
    public ResponseEntity<List<PlayGroundForGet>> findAllByMainAddress(@RequestParam String mainAddress) {
        List<PlayGroundEntity> playGroundEntityList = playGroundRepository.findPlayGroundListByMainAddress(mainAddress);

        if (playGroundEntityList.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        List<PlayGroundForGet> playGroundList = playGroundEntityList.stream().map(PlayGroundForGet::from).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(playGroundList);
    }

    @Operation(summary = "운동장 이름으로 운동장 조회")
    @GetMapping("/playGround/groundName")
    public ResponseEntity<List<PlayGroundForGet>> findAllByGroundName(@RequestParam String name) {
        List<PlayGroundEntity> playGroundEntityList = playGroundRepository.findPlayGroundListByGroundName(name);

        if (playGroundEntityList.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        List<PlayGroundForGet> playGroundList = playGroundEntityList.stream().map(PlayGroundForGet::from).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(playGroundList);
    }

}
