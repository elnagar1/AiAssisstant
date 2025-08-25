# TestAnalyistAiAssisstant - مولد سيناريوهات اختبار الأداء الذكي

## نظرة عامة

مشروع متقدم لتوليد سيناريوهات اختبار الأداء باستخدام الذكاء الاصطناعي. يقوم النظام بتحليل متطلبات النظام وتوليد خطط اختبار أداء شاملة ومفصلة.

## المميزات الرئيسية

### 🤖 الذكاء الاصطناعي في توليد السيناريوهات
- **تحليل ذكي للمتطلبات**: يحلل النظام نوع النظام وعدد المستخدمين والعمليات الشائعة
- **توليد تلقائي للسيناريوهات**: يولد 5 أنواع من سيناريوهات الاختبار:
  - **اختبار الضغط (Stress Testing)**: تحديد نقطة انهيار النظام
  - **اختبار التحمل (Soak Testing)**: اختبار الاستقرار على المدى الطويل
  - **اختبار الحجم (Volume Testing)**: اختبار مع البيانات الضخمة
  - **اختبار الذروة (Spike Testing)**: اختبار الاستجابة للزيادة المفاجئة
  - **اختبار قابلية التوسع (Scalability Testing)**: اختبار الأداء مع إضافة موارد

### 📊 تحليل متقدم للنظام
- **حساب نقطة الانهيار**: يحدد الحد الأقصى للمستخدمين المتزامنين
- **تحليل العمليات**: يزن العمليات المختلفة حسب استهلاك الموارد
- **تقييم المخاطر**: يحدد المخاطر المحتملة لكل سيناريو
- **تقدير المدة**: يحسب الوقت المتوقع لتنفيذ جميع الاختبارات

### 🎯 تخصيص ذكي
- **أنواع الأنظمة المدعومة**:
  - متجر إلكتروني
  - نظام مصرفي
  - نظام صحي
  - نظام تعليمي
  - نظام حكومي
  - وسائل التواصل الاجتماعي
  - نظام ألعاب
  - نظام مؤسسي

- **بيئات النشر**:
  - بيئة التطوير
  - بيئة الاختبار
  - بيئة مشابهة للإنتاج
  - بيئة الإنتاج

## التقنيات المستخدمة

- **Backend**: Spring Boot 3.2.0
- **Frontend**: HTML5, CSS3, JavaScript (Vanilla)
- **Template Engine**: Thymeleaf
- **Build Tool**: Maven
- **Java Version**: 17

## كيفية الاستخدام

### 1. تشغيل التطبيق
```bash
mvn spring-boot:run
```

### 2. الوصول للتطبيق
افتح المتصفح واذهب إلى: `http://localhost:8080`

### 3. إدخال معلومات النظام
- اختر نوع النظام
- أدخل عدد المستخدمين المتوقعين
- حدد حجم البيانات
- اختر بيئة النشر
- حدد العمليات الشائعة
- أدخل أوقات الذروة

### 4. توليد السيناريوهات
اضغط على زر "توليد سيناريوهات اختبار الأداء" وسيقوم النظام بإنشاء خطة شاملة.

## API Endpoints

### POST `/api/performance-test/generate-scenarios`
توليد سيناريوهات اختبار الأداء

**Request Body:**
```json
{
  "systemType": "E_COMMERCE",
  "expectedUsers": 1000,
  "dataVolume": 100000,
  "deploymentEnvironment": "STAGING",
  "commonOperations": ["تسجيل الدخول", "البحث", "إضافة منتج"],
  "peakTimes": {
    "morning": 500,
    "afternoon": 800,
    "evening": 1200
  }
}
```

### GET `/api/performance-test/scenario-types`
الحصول على أنواع السيناريوهات المدعومة

### GET `/api/performance-test/system-types`
الحصول على أنواع الأنظمة المدعومة

### GET `/api/performance-test/deployment-environments`
الحصول على بيئات النشر المدعومة

## بنية المشروع

```
src/main/java/Madfoat/Learning/
├── controller/
│   ├── PerformanceTestController.java    # API Controller
│   └── WebController.java               # Web Pages Controller
├── service/
│   └── PerformanceTestScenarioGenerator.java  # AI Service
├── dto/
│   ├── PerformanceTestRequest.java      # Request Model
│   ├── PerformanceTestScenario.java     # Scenario Model
│   └── PerformanceTestPlan.java         # Plan Model
└── Main.java
```

## مثال على النتيجة

```json
{
  "planId": "PERF_PLAN_20241201_143022",
  "planName": "خطة اختبار الأداء - E_COMMERCE",
  "systemName": "E_COMMERCE",
  "createdDate": "2024-12-01T14:30:22",
  "createdBy": "AI Generator",
  "scenarios": [
    {
      "scenarioId": "STRESS_001",
      "scenarioName": "اختبار الضغط - نقطة الانهيار",
      "scenarioType": "STRESS",
      "description": "اختبار النظام تحت ضغط عالي حتى نقطة الانهيار",
      "targetUsers": 1500,
      "rampUpTime": 30,
      "testDuration": 30,
      "priority": 5,
      "testSteps": [
        "بدء بـ 300 مستخدم",
        "زيادة تدريجية إلى 750 مستخدم",
        "زيادة إلى 1200 مستخدم",
        "زيادة إلى 1500 مستخدم (نقطة الانهيار المتوقعة)",
        "مراقبة استجابة النظام"
      ],
      "successCriteria": [
        "تحديد نقطة الانهيار بدقة",
        "قياس وقت الاستجابة عند كل مستوى",
        "تحديد أول مؤشر للتراجع في الأداء"
      ],
      "monitoringPoints": [
        "استخدام CPU",
        "استخدام الذاكرة",
        "وقت الاستجابة",
        "معدل الأخطاء",
        "استخدام الشبكة"
      ],
      "expectedOutcome": "تحديد الحد الأقصى للمستخدمين المتزامنين"
    }
  ],
  "estimatedDuration": "26 ساعة و 15 دقيقة",
  "riskAssessment": "تقييم المخاطر:\n1. مخاطر عالية: اختبار الضغط قد يسبب توقف النظام\n2. مخاطر متوسطة: اختبار التحمل قد يستهلك موارد كثيرة\n3. مخاطر منخفضة: اختبار الحجم قد يؤثر على الأداء مؤقتاً\n4. توصيات: إعداد خطة استرداد ومراقبة مستمرة"
}
```

## المساهمة

نرحب بالمساهمات! يرجى اتباع الخطوات التالية:

1. Fork المشروع
2. إنشاء فرع جديد للميزة (`git checkout -b feature/AmazingFeature`)
3. Commit التغييرات (`git commit -m 'Add some AmazingFeature'`)
4. Push إلى الفرع (`git push origin feature/AmazingFeature`)
5. فتح Pull Request

## الترخيص

هذا المشروع مرخص تحت رخصة MIT - انظر ملف [LICENSE](LICENSE) للتفاصيل.

## الدعم

إذا واجهت أي مشاكل أو لديك أسئلة، يرجى فتح issue في GitHub أو التواصل مع فريق التطوير.

---

**تم تطوير هذا المشروع بواسطة فريق Madfoat Learning** 🚀
