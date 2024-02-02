package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.stereotype.Component;

/**
 * 1. @Autowired 매칭
 * [기존] @Autowired private DiscountPolicy discountPolicy
 * [필드명을 빈이름으로 변경] @Autowired private DiscountPolicy rateDiscountPolicy
 * 1) 타입 매칭
 * 2) 타입 매칭 결과가 2개 이상일 때, 타입명, 파라미터명으로 빈 이름 매칭
 *
 * 2. @Qualifier 매칭
 * 주입 시 추가적인 방법을 제공하는 것 (빈 이름 변경하는 것 아님)
 * Qualifier 끼리 매칭할 때만 사용 권장
 *
 * 3. @Primary 매칭
 * 우선 순위 지정하는 방법
 *
 * @Qualifier vs @Primary
 * 직접 등록하는 @Qualifier가 우선권이 높음.
 * => 만약 두가지 사용 시 메인에 @Primary를 붙이고
 * 서브에 @Qualifier를 붙여 상세하게 사용하도록 설계하는 것이 일반적.
 */
@Component
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // @Qualifier 사용 시
//    public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    // 생성한 어노테이션 사용 시
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }


    // @Primary 사용 시
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
