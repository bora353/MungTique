import { useEffect, useState } from "react";
import { FaCalendarAlt, FaDog, FaMapMarkerAlt } from "react-icons/fa";
import { useNavigate } from "react-router-dom";

interface PetShop {
  id: number;
  name: string;
  description: string;
  imageUrl: string;
  rating: number;
}

export default function MainContainer() {
  const navigate = useNavigate();
  const [featuredShops, setFeaturedShops] = useState<PetShop[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);

  useEffect(() => {
    // 실제 구현에서는 API에서 데이터를 가져올 수 있음
    // 여기서는 예시 데이터 사용
    const dummyData: PetShop[] = [
      {
        id: 1,
        name: "멍멍살롱",
        description: "프리미엄 애견 미용 서비스",
        imageUrl: "/api/placeholder/300/200",
        rating: 4.8,
      },
      {
        id: 2,
        name: "퍼피 스파",
        description: "편안한 스파와 그루밍 서비스",
        imageUrl: "/api/placeholder/300/200",
        rating: 4.7,
      },
      {
        id: 3,
        name: "바우와우",
        description: "전문 애견 케어 서비스",
        imageUrl: "/api/placeholder/300/200",
        rating: 4.9,
      },
      {
        id: 4,
        name: "해피테일",
        description: "친절한 서비스와.편안한 환경",
        imageUrl: "/api/placeholder/300/200",
        rating: 4.6,
      },
    ];

    setFeaturedShops(dummyData);
    setIsLoading(false);
  }, []);


  const handleClick = () => {
    navigate("/mungshop"); 
  };

  return (
    <div className="min-h-screen bg-pink-50">
      {/* 메인 배너 */}
      <section className="bg-gradient-to-r from-pink-300 to-purple-300 py-16">
        <div className="container mx-auto px-4 text-center">
          <h2 className="text-4xl font-bold text-white mb-6">
            내 반려견을 위한 특별한 공간
          </h2>
          <p className="text-xl text-white mb-8">
            뭉티끄에서 쉽고 빠르게 예약하고 사랑하는 반려견에게 최고의 케어를
            선물하세요
          </p>
          <button className="bg-white text-pink-500 font-bold py-3 px-8 rounded-full shadow-lg hover:bg-pink-100 transition duration-300 flex items-center mx-auto"
            onClick={handleClick}>
            <FaCalendarAlt className="mr-2" />
            지금 예약하기
          </button>
        </div>
      </section>

      {/* 서비스 소개 */}
      <section className="py-12 container mx-auto px-4">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div className="bg-white rounded-lg shadow-md p-6 text-center hover:shadow-lg transition duration-300">
            <div className="bg-pink-100 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
              <FaDog className="text-2xl text-pink-500" />
            </div>
            <h3 className="text-xl font-bold text-gray-800 mb-2">
              프리미엄 그루밍
            </h3>
            <p className="text-gray-600">
              전문 그루머가 제공하는 맞춤형 미용 서비스로 반려견을 더 사랑스럽게
            </p>
          </div>
          <div className="bg-white rounded-lg shadow-md p-6 text-center hover:shadow-lg transition duration-300">
            <div className="bg-pink-100 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
              <FaCalendarAlt className="text-2xl text-pink-500" />
            </div>
            <h3 className="text-xl font-bold text-gray-800 mb-2">
              간편한 예약
            </h3>
            <p className="text-gray-600">
              원하는 시간에 원하는 애견샵을 몇 번의 클릭만으로 손쉽게 예약
            </p>
          </div>
          <div className="bg-white rounded-lg shadow-md p-6 text-center hover:shadow-lg transition duration-300">
            <div className="bg-pink-100 w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
              <FaMapMarkerAlt className="text-2xl text-pink-500" />
            </div>
            <h3 className="text-xl font-bold text-gray-800 mb-2">
              위치 기반 검색
            </h3>
            <p className="text-gray-600">
              내 주변의 애견샵을 쉽게 찾고 리뷰와 평점을 확인
            </p>
          </div>
        </div>
      </section>

      {/* 인기 애견샵 */}
      <section className="py-12 bg-pink-100">
        <div className="container mx-auto px-4">
          <h2 className="text-3xl font-bold text-center text-gray-800 mb-12">
            인기 애견샵
          </h2>
          {isLoading ? (
            <div className="text-center">로딩 중...</div>
          ) : (
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
              {featuredShops.map((shop) => (
                <div
                  key={shop.id}
                  className="bg-white rounded-lg overflow-hidden shadow-md hover:shadow-xl transition duration-300"
                >
                  <img
                    src={shop.imageUrl}
                    alt={shop.name}
                    className="w-full h-48 object-cover"
                  />
                  <div className="p-4">
                    <div className="flex justify-between items-center mb-2">
                      <h3 className="text-lg font-bold text-gray-800">
                        {shop.name}
                      </h3>
                      <div className="flex items-center text-yellow-500">
                        <span className="text-sm font-medium ml-1">
                          ★ {shop.rating}
                        </span>
                      </div>
                    </div>
                    <p className="text-gray-600 text-sm mb-4">
                      {shop.description}
                    </p>
                    <button className="w-full bg-pink-500 text-white py-2 rounded hover:bg-pink-600 transition duration-300">
                      예약하기
                    </button>
                  </div>
                </div>
              ))}
            </div>
          )}
          <div className="text-center mt-8">
            <a
              href="/shops"
              className="inline-block text-pink-500 font-medium hover:text-pink-600 hover:underline"
            >
              모든 애견샵 보기 →
            </a>
          </div>
        </div>
      </section>

      {/* 고객 리뷰 */}
      <section className="py-12 container mx-auto px-4">
        <h2 className="text-3xl font-bold text-center text-gray-800 mb-12">
          행복한 고객들의 이야기
        </h2>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          <div className="bg-white p-6 rounded-lg shadow-md">
            <div className="flex items-center mb-4">
              <img
                src="/api/placeholder/50/50"
                alt="고객"
                className="w-12 h-12 rounded-full mr-4"
              />
              <div>
                <h4 className="font-bold text-gray-800">김지민</h4>
                <div className="flex text-yellow-500">★★★★★</div>
              </div>
            </div>
            <p className="text-gray-600">
              "멍멍살롱에서 우리 강아지 미용을 맡겼는데, 너무 만족스러워요!
              예약도 쉽게 할 수 있었고, 전문적인 서비스에 감동했습니다."
            </p>
          </div>
          <div className="bg-white p-6 rounded-lg shadow-md">
            <div className="flex items-center mb-4">
              <img
                src="/api/placeholder/50/50"
                alt="고객"
                className="w-12 h-12 rounded-full mr-4"
              />
              <div>
                <h4 className="font-bold text-gray-800">이민준</h4>
                <div className="flex text-yellow-500">★★★★★</div>
              </div>
            </div>
            <p className="text-gray-600">
              "mungtique 덕분에 집 근처에 있는 좋은 애견샵을 알게 되었어요. 이제
              정기적으로 이용하고 있습니다. 편리한 예약 시스템이 정말 좋아요!"
            </p>
          </div>
        </div>
      </section>

      {/* 푸터 */}
      <footer className="bg-gray-800 text-white py-8">
        <div className="container mx-auto px-4">
          <div className="flex flex-col md:flex-row justify-between">
            <div className="mb-6 md:mb-0">
              <div className="flex items-center">
                <FaDog className="text-pink-400 text-2xl mr-2" />
                <h2 className="text-xl font-bold">뭉티끄</h2>
              </div>
              <p className="mt-2 text-gray-400">반려견을 위한 최고의 선택</p>
            </div>
            <div className="grid grid-cols-2 md:grid-cols-3 gap-8">
              <div>
                <h3 className="text-lg font-semibold mb-2">서비스</h3>
                <ul className="space-y-2 text-gray-400">
                  <li>
                    <a href="#" className="hover:text-pink-400">
                      애견샵 찾기
                    </a>
                  </li>
                  <li>
                    <a href="#" className="hover:text-pink-400">
                      예약하기
                    </a>
                  </li>
                  <li>
                    <a href="#" className="hover:text-pink-400">
                      리뷰 작성
                    </a>
                  </li>
                </ul>
              </div>
              <div>
                <h3 className="text-lg font-semibold mb-2">회사 소개</h3>
                <ul className="space-y-2 text-gray-400">
                  <li>
                    <a href="#" className="hover:text-pink-400">
                      소개
                    </a>
                  </li>
                  <li>
                    <a href="#" className="hover:text-pink-400">
                      이용약관
                    </a>
                  </li>
                  <li>
                    <a href="#" className="hover:text-pink-400">
                      개인정보 처리방침
                    </a>
                  </li>
                </ul>
              </div>
              <div>
                <h3 className="text-lg font-semibold mb-2">고객 지원</h3>
                <ul className="space-y-2 text-gray-400">
                  <li>
                    <a href="#" className="hover:text-pink-400">
                      문의하기
                    </a>
                  </li>
                  <li>
                    <a href="#" className="hover:text-pink-400">
                      자주 묻는 질문
                    </a>
                  </li>
                  <li>
                    <a href="#" className="hover:text-pink-400">
                      공지사항
                    </a>
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div className="mt-8 pt-8 border-t border-gray-700 text-center text-gray-400">
            <p>&copy; 2025 mungtique. All rights reserved.</p>
          </div>
        </div>
      </footer>
    </div>
  );
}
