package com.example.nuvi_demo.Controller;



import com.example.nuvi_demo.domain.Entity.User;
import com.example.nuvi_demo.Exception.CUserNotFoundException;
import com.example.nuvi_demo.Repo.UserJpaRepo;
import com.example.nuvi_demo.model.response.*;
import com.example.nuvi_demo.service.user.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

//import io.swagger.v3.oas.annotations.*;
@Api(tags = {"3. User"})
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping(value = "/v1")
public class UserController {

    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService; // 결과를 처리할 Service

    //핵심은 APIImplicitParams다 이는 API 호출에서 헤더가 필요할 경우 사용하는데 헤더에 X-AUTH-TOKEN을 인자로 받도록 설정하는 것이다.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser() {
        // 결과데이터가 여러건인경우 getListResult를 이용해서 결과를 출력한다.
        return responseService.getListResult(userJpaRepo.findAll());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 조회", notes = "회원id(uid)로 회원을 조회한다")
    @GetMapping(value = "/user")
    public SingleResult<User> findUser(@AuthenticationPrincipal User check_user) {
        //이 어노테이션이 핵심이다 AuthenticationPrincipal을 USer객체로 받는것
        // SecurityContext에서 인증받은 회원의 정보를 얻어온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authentication 정보: "+authentication.toString());
        log.info("authentication get name 정보: "+ check_user.getUser_id());
//        authentication.getPrincipal().getClass();

        String id = check_user.getUser_id();
        // 결과데이터가 단일건인경우 getSingleResult를 이용해서 결과를 출력한다.
        return responseService.getSingleResult(userJpaRepo.findById(id).orElseThrow(CUserNotFoundException::new));

    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
//            @ApiParam(value = "회원번호", required = true) @RequestParam String user_id,
            @ApiParam(value = "회원이름", required = true) @RequestParam String nick_name) {
        User user = User.builder()
//                .user_id(user_id) //user_id는 pk이니깐 일단 보류
                .nick_name(nick_name)
                .build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "회원번호(user_id)로 회원정보를 삭제한다")
    @DeleteMapping(value = "/user/{user_id}")
    public CommonResult delete(
            @ApiParam(value = "회원번호", required = true) @PathVariable String user_id) {
        userJpaRepo.deleteById(user_id);

        // 성공 결과 정보만 필요한경우 getSuccessResult()를 이용하여 결과를 출력한다.
        return responseService.getSuccessResult();
    }
}