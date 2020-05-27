// load in intelliJ for Controller

package com.example.pjt3.Controller;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test2Controller {
    static public class Morpheme {
        final String text;
        final String type;
        Integer count;
        public Morpheme (String text, String type, Integer count) {
            this.text = text;
            this.type = type;
            this.count = count;
        }
    }
    static public class NameEntity {
        final String text;
        final String type;
        Integer count;
        public NameEntity (String text, String type, Integer count) {
            this.text = text;
            this.type = type;
            this.count = count;
        }
    }
    @GetMapping("/test2")
    static public void main(String[] args) {
        String openApiURL = "http://aiopen.etri.re.kr:8000/WiseNLU";
        String accessKey = "0fb8d092-a504-4f73-aade-eeba691f7cd5";    // 발급받은 API Key
        String analysisCode = "srl";   // 언어 분석 코드
        String text = "지원 직무 관련 경험 또는 리더십을 발휘하여 주어진 성과를 달성한 경험에 대해 기술해주시기 바랍니다. (당시 상황, 본인의 해결방법, 결과 포함) 1000자 (영문작성 시 2000자) 이내\n";           // 분석할 텍스트 데이터
        Gson gson = new Gson();

        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();

        argument.put("analysis_code", analysisCode);
        argument.put("text", text);

        request.put("access_key", accessKey);
        request.put("argument", argument);

        URL url;
        Integer responseCode = null;
        String responBodyJson = null;
        Map<String, Object> responeBody = null;

        try {
            url = new URL(openApiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(gson.toJson(request).getBytes("UTF-8"));
            wr.flush();
            wr.close();

            responseCode = con.getResponseCode();
            InputStream is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();

            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            responBodyJson = sb.toString();

            // http 요청 오류 시 처리
            if ( responseCode != 200 ) {
                // 오류 내용 출력
                System.out.println("[error] " + responBodyJson);
                return ;
            }

            responeBody = gson.fromJson(responBodyJson, Map.class);
            Integer result = ((Double) responeBody.get("result")).intValue();
            Map<String, Object> returnObject;
            List<Map> sentences;

            // 분석 요청 오류 시 처리
            if ( result != 0 ) {

                // 오류 내용 출력
                System.out.println("[error] " + responeBody.get("result"));
                return ;
            }

            // 분석 결과 활용
            returnObject = (Map<String, Object>) responeBody.get("return_object");
            sentences = (List<Map>) returnObject.get("sentence");

            Map<String, Morpheme> morphemesMap = new HashMap<String, Morpheme>();
            Map<String, NameEntity> nameEntitiesMap = new HashMap<String, NameEntity>();
            List<Morpheme> morphemes = null;
            List<NameEntity> nameEntities = null;
            List target = new ArrayList(); // new ArrayList<>() 도 가능


            for( Map<String, Object> sentence : sentences ) {
                List<Map<String, Object>> srlResult = (List<Map<String, Object>>) sentence.get("SRL");
                for( Map<String, Object> srlInfo : srlResult ) {
                    List<Map> arguments = (List<Map>) srlInfo.get("argument");


                    for (Map<String, Object> argu : arguments) {
                        System.out.println("===============text==========");
                        String name = (String )argu.get("text");
                        System.out.println(name);
                        System.out.println("===============code==========");
                        String nameType = (String )argu.get("type");
                        if (nameType.equals("ARG0")) {
                            String nameMean="행동, 경험주";
                            System.out.println(nameMean);

                            target.add(name);

                        }
                        else if (nameType.equals("ARG1")) {
                            String nameMean="행위의 대상";
                            System.out.println(nameMean);

                            target.add(name);

                        }
                        else if (nameType.equals("ARG2")) {
                            String nameMean="수혜자, 결과";
                            System.out.println(nameMean);
                        }
                        else if (nameType.equals("ARG3")) {
                            String nameMean="기점";
                            System.out.println(nameMean);
                        }
                        else if (nameType.equals("ARG4")) {
                            String nameMean="착점";
                            System.out.println(nameMean);
                        }
                        else if (nameType.equals("ARGM-TMP")) {
                            String nameMean="시간";
                            System.out.println(nameMean);
                        }
                        else if (nameType.equals("ARGM-LOC")) {
                            String nameMean="장소";
                            System.out.println(nameMean);
                        }
                        else if (nameType.equals("ARGM-DIR")) {
                            String nameMean="방향";
                            System.out.println(nameMean);
                        }
                        else if (nameType.equals("ARGM-MNR")) {
                            String nameMean="방법";
                            System.out.println(nameMean);
                        }
                        else if (nameType.equals("ARGM-PRP")) {
                            String nameMean="목적";
                            System.out.println(nameMean);

                            target.add(name);

                        }
                        else if (nameType.equals("ARGM-CAU")) {
                            String nameMean="원인";
                            System.out.println(nameMean);

                        }
                        else if (nameType.equals("ARGM-EXT")) {
                            String nameMean="범위, 정도";
                            System.out.println(nameMean);
                        }
                        else if (nameType.equals("ARGM-CND")) {
                            String nameMean="조건";
                            System.out.println(nameMean);
                        }
                        else if (nameType.equals("ARGM-PRD")) {
                            String nameMean="보조서술";
                            System.out.println(nameMean);
                        }
                        else if (nameType.equals("ARGM-DIS")) {
                            String nameMean="담화";
                            System.out.println(nameMean);
                        }
                        else if (nameType.equals("ARGM-ADV")) {
                            String nameMean="부사구";
                            System.out.println(nameMean);
                        }
                        else if (nameType.equals("ARGM-NEG")) {
                            String nameMean="부정";
                            System.out.println(nameMean);
                        };

                    }
                }


                // 형태소 분석기 결과 수집 및 정렬
//                List<Map<String, Object>> morphologicalAnalysisResult = (List<Map<String, Object>>) sentence.get("morp");
//                for( Map<String, Object> morphemeInfo : morphologicalAnalysisResult ) {
//                    String lemma = (String) morphemeInfo.get("lemma");
//                    Morpheme morpheme = morphemesMap.get(lemma);
//                    if ( morpheme == null ) {
//                        morpheme = new Morpheme(lemma, (String) morphemeInfo.get("type"), 1);
//                        morphemesMap.put(lemma, morpheme);
//                    } else {
//                        morpheme.count = morpheme.count + 1;
//                    }
//                }
//
//
            }

//            if ( 0 < morphemesMap.size() ) {
//                morphemes = new ArrayList<Morpheme>(morphemesMap.values());
//                morphemes.sort( (morpheme1, morpheme2) -> {
//                    return morpheme2.count - morpheme1.count;
//                });
//            }
//
//            if ( 0 < nameEntitiesMap.size() ) {
//                nameEntities = new ArrayList<NameEntity>(nameEntitiesMap.values());
//                nameEntities.sort( (nameEntity1, nameEntity2) -> {
//                    return nameEntity2.count - nameEntity1.count;
//                });
//            }

            // 형태소들 중 명사들에 대해서 많이 노출된 순으로 출력 ( 최대 5개 )
//            morphemes
//                    .stream()
//                    .filter(morpheme -> {
//                        return morpheme.type.equals("NNG") ||
//                                morpheme.type.equals("NNP") ||
//                                morpheme.type.equals("NNB");
//                    })
//                    .limit(50)
//                    .forEach(morpheme -> {
//                        System.out.println("[명사] " + morpheme.text + " ("+morpheme.count+")" );
//                    });
            System.out.println(target);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
