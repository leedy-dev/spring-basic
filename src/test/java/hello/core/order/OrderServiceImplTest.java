package hello.core.order;

import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceImplTest {

    @Test
    void createOrder() {
        /*
          생성자 주입 권장 이유
          1. 수정자 주입을 사용하면 의존관계 주입이 되지 않을 때 NPE가 발생하지만
          생성자 주입을 사용하면 필수 값이 누락되었기 때문에 컴파일 오류 발생하게 된다.

          2. 생성자 주입은 final 필드를 사용할 수 있으므로
          필수 값이 설정되지 않으면 컴파일 오류가 발생하며, 변경 가능성을 막을 수 있다.

          => 프레임워크에 의존하지 않고 순수 자바 코드의 특징을 이용할 수 있다.

          => 항상 생성자 주입을 사용하되
          가끔 옵션이 필요할 경우 수정자 주입을 사용 (실무에서 거의 드물다.)
          필드 주입은 사용하지 말 것

          => 아래 코드는 생성자 주입인데 필수 값을 입력하지 않으면 컴파일 오류가 발생한다.
          new 키워드로 생성하여 넣어 해결할 수 있다.
          이렇게 프레임워크에 의존하지 않고 순수 자바 코드로 테스트가 가능하다.
         */
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "memberA", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new RateDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);

        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

}