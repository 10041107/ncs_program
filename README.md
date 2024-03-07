## 아래의 프로그램은 메뉴 관리 프로그램이다.

### 그러나 페이지에서 등록, 수정, 삭제 등의 요청을 하는 경우 페이지를 찾을 수 없는 오류가 발생한다.
### 다음 오류의 원인과 오류를 해결하는 코드를 작성하여 github의 url을 첨부합니다.

-----
-----


## 1. regist 쿼리 문제
### MyBatis XML 매퍼 파일의 regist 쿼리에 문제가 있습니다.


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


이후 @ExceptionHandler 메소드가 더 이상 SQLSyntaxErrorException을 발생시키지 않게 됩니다.


-----
-----


## 2. index.html 파일 문제
### index.html의 경로 호출부분에 문제가 있습니다. 

```
  <h3>3. 신규 메뉴 등록</h3>
  <button onclick="location.href='regist'">신규 메뉴 등록하기</button>

  <h3>4. 메뉴 수정</h3>
  <button onclick="location.href='update'">메뉴 수정하기</button>

  <h3>5. 메뉴 삭제</h3>
  <button onclick="location.href='delete'">메뉴 삭제하기</button>
  ```
해당 부분의 regist, update, delete를 호출하는 href의 경로가 잘못되었습니다.
이렇게 되면 'http://localhost:8080/delete(regist,update....)' 방식으로 호출하게 됩니다.

올바른 경로는
' http://localhost:8080/menus/delete(regist,update..)' 입니다.
수정된 index 파일은 다음과 같습니다.

```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>메뉴 키오스크</title>
</head>
<body>
  <h1>메뉴 키오스크</h1>
  <h3>1. 전체 메뉴 조회</h3>
  <button onclick="location.href='menus/menus'">전체 메뉴 조회하기</button>

<h3>2. 단일 메뉴 조회</h3>
<button onclick="location.href='menus/onemenu'">단일 메뉴 조회하기</button>

<h3>3. 신규 메뉴 등록</h3>
<button onclick="location.href='menus/regist'">신규 메뉴 등록하기</button>

<h3>4. 메뉴 수정</h3>
<button onclick="location.href='menus/update'">메뉴 수정하기</button>

<h3>5. 메뉴 삭제</h3>
<button onclick="location.href='menus/delete'">메뉴 삭제하기</button>

<h3>3. 사용자 로그인</h3>
  <form action="/menus/login" method="post">
    아이디   : <input type="text" name="id"/> <br>
    비밀번호 : <input type= "password" name="pwd"/>
    <button>요청하기</button>
  </form>




 <!-- a태그 href 랑 button 태그 location.href 차이는 자바스크립트 사용유무. 기능은 차이 없음-->
</body>
</html>
```

이후 @ExceptionHandler 메소드가 더 이상 NoResourceFoundException을 발생시키지 않게 됩니다.
