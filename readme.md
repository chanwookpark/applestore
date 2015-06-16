# 이곳은...
다양한 Commerce architecture를 실험하기 위한 공간입니다. 

# 혹시 돌려보고 싶다면..

1. 우선 Solr 4.7.2 버전을 설치하시고
1. 그리고 Maria DB를 설치하셔야 합니다.
1. 그리고 코드를 git으로 내려 받으세요 (저는 intelliJ를 사용합니다)
1. 그 다음 root의 pom으로 'clean install'을 실행하시고 
1. 프론트 화면은 'applestore-front'의 pom으로 'spring-boot:run'을 실행하시고
1. 백오피스 화면은 'applestore-admin'의 pom으로 'spring-boot:run'을 실행하면 됩니다. 
1. 각각 포트는 프론트는 10001번, 백오피스는 10002번 입니다. 
  
# 실험 시나리오 
1. Solr + RDB(ORM)으로 상품 리스트 만들어보기   
2. JDK 8의 나르손에서 서버 사이드 스크립트 렌더링 엔진(코드명 r2) 만들어 실험하기 