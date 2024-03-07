## 아래의 프로그램은 메뉴 관리 프로그램이다. 

### 그러나 페이지에서 등록, 수정, 삭제 등의 요청을 하는 경우 페이지를 찾을 수 없는 오류가 발생한다. 
### 다음 오류의 원인과 오류를 해결하는 코드를 작성하여 github의 url을 첨부합니다.

-----


MyBatis XML 매퍼 파일의 regist 쿼리에 문제가 있습니다.


이 쿼리는 `${}` 문법을 사용하여 변수를 문자열로 삽입하려고 합니다.  
그러나 ${} 문법은 SQL 인젝션 공격에 취약하므로 이를 사용해서는 안 됩니다.  
대신에 #{} 문법을 사용해야 합니다.


regist 쿼리를 다음과 같이 수정해야 합니다.

```
<insert id="regist" parameterType="com.ohgiraffers.pos.menu.dto.MenuDTO">
    INSERT INTO TBL_MENU(
        MENU_CODE,
        MENU_NAME,
        MENU_PRICE,
        CATEGORY_CODE,
        ORDERABLE_STATUS
    ) VALUES(
        #{code},
        #{name},
        #{price},
        #{categoryCode},
        'Y'
    )
</insert>
```

이렇게 하면 MyBatis가 변수를 안전하게 처리하여 SQL 인젝션 공격을 방지할 수 있습니다.

수정된 매퍼 파일은 다음과 같습니다.

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.ohgiraffers.pos.menu.model.MenuDAO">

    <resultMap id="menuResult" type="com.ohgiraffers.pos.menu.dto.MenuDTO">
        <id property="code" column="MENU_CODE"/>
        <result property="name" column="MENU_NAME"/>
        <result property="price" column="MENU_PRICE"/>
        <result property="categoryCode" column="CATEGORY_CODE"/>
        <result property="status" column="ORDERABLE_STATUS"/>
    </resultMap>

    <select id="selectAllMenu" resultMap="menuResult">
        SELECT
            *
          FROM TBL_MENU
         WHERE ORDERABLE_STATUS='Y'
    </select>

    <select id="selectAllName" resultMap="menuResult">
        SELECT
            MENU_NAME
          FROM TBL_MENU
    </select>

    <select id="selectOneMenu" resultMap="menuResult">
        SELECT
            *
          FROM TBL_MENU
         WHERE MENU_CODE = #{code}
    </select>

    <insert id="regist" parameterType="com.ohgiraffers.pos.menu.dto.MenuDTO">
        INSERT INTO TBL_MENU(
            MENU_CODE,
            MENU_NAME,
            MENU_PRICE,
            CATEGORY_CODE,
            ORDERABLE_STATUS
        ) VALUES(
            #{code},
            #{name},
            #{price},
            #{categoryCode},
            'Y'
        )
    </insert>

    <update id="update" parameterType="com.ohgiraffers.pos.menu.dto.MenuDTO">
        UPDATE TBL_MENU
           SET MENU_NAME= #{name},
               MENU_PRICE= #{price},
               CATEGORY_CODE= #{categoryCode}
         WHERE MENU_CODE= #{code}
    </update>

    <delete id="delete" parameterType="com.ohgiraffers.pos.menu.dto.MenuDTO">
        DELETE
        FROM TBL_MENU
        WHERE MENU_CODE= #{code}
    </delete>

</mapper>
```


이로서 @ExceptionHandler 메소드가 더 이상 SQLSyntaxErrorException을 발생시키지 않을 것으로 기대됩니다.