import React, { useState, useCallback, useEffect } from 'react';
import { SERVER_URL } from '../../server/getServer';
import { Link } from '../../components';
import * as U from '../../utils';
import axios from 'axios';
import type { ChangeEvent } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts';
import QRCode from 'qrcode.react'; // QRCode 라이브러리를 가져옵니다.

const MyPage: React.FC = () => {
  const [user, setUser] = useState<any | null>(null);
  const [jwt, setJwt] = useState<string>('');
  const [jwtbool, setJwtbool] = useState<boolean>(false);
  const [showQRCode, setShowQRCode] = useState<boolean>(false); // QR 코드를 표시할지 여부를 추적하는 상태
  const { logout } = useAuth();

  useEffect(() => {
    U.readStringP('jwt').then((jwt) => {
      setJwt(jwt ?? '');
      setJwtbool(true);
    });
    if (jwtbool) {
      axios(SERVER_URL + '/member', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `otb ${jwt}`,
        },
      })
        .then((res) => {
          setUser(res.data.data[0]);
        })
        .catch((error) => {});
    }
  }, [jwt]);

  const deleteuser = () => {
    if (window.confirm('삭제하시겠습니까?')) {
      axios
        .delete(SERVER_URL + '/member', {
          headers: {
            'Content-Type': 'application/json',
            Authorization: `otb ${jwt}`,
          },
        })
        .then((res) => {
          if (res.data.status === 201) {
            alert('삭제되었습니다');
            logout(() => {
              navigate('/'); // 홈 페이지로 이동합니다
            });
          }
        })
        .catch((error) => {
          alert('오류! 다시 입력해주세요.');
        });
    } else {
      // 삭제 취소
    }
  };

  const [showEditForm, setShowEditForm] = useState(false);

  const toggleEditForm = () => {
    setShowEditForm((prevState) => !prevState);
  };

  const [formData, setFormData] = useState({
    newEmail: '',
    newPassword: '',
    // 다른 수정 정보들을 추가하세요
  });

  const handleFormChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const navigate = useNavigate();
  const handleUpdate = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    axios({
      method: 'POST',
      url: SERVER_URL + '/member',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `otb ${jwt}`,
      },
      data: {
        email: formData.newEmail,
        password: formData.newPassword,
      },
    })
      .then((res) => {
        if (res.data.status === 201) {
          alert(res.data.message);
          navigate('/');
        }
      })
      .catch((error) => {
        console.log(error.data);
      });
  };

  return (
    <div>
      <div className="flex justify-between bg-gray-100">
        <div className="flex p-2">
          <Link to="/" className="ml-1">
            <img
              src="/img/otblogogogo.png"
              alt="OTB(우비) 로고"
              className="w-12.5 h-12.5 bg-gray-100"
            />
          </Link>
        </div>
      </div>
      <div className="flex flex-col min-h-screen border-gray-300 rounded-xl shadow-xl bg-gray-100 border">
        <div className="flex flex-col items-center  flex-1 max-w-sm px-2 mx-auto">
          <div className="w-full px-6 py-8 text-black bg-white rounded shadow-md">
            <div>
              <h1 className="mb-8 text-4xl text-center text-lime-500">
                마이 페이지
              </h1>
              {user ? (
                <div className="mb-8 text-2xl text-center text-black-500">
                  <p>Username: {user.username}</p>
                  <p>Email: {user.email}</p>
                  <button
                    className="flex-center ml-4 mr-4 btn btn-primary text-white border-lime-600 bg-lime-600"
                    onClick={toggleEditForm}
                  >
                    수정하기
                  </button>
                  <button
                    className="flex-center ml-4 mr-4 btn btn-primary text-white border-lime-600 bg-lime-600"
                    onClick={deleteuser}
                  >
                    회원탈퇴
                  </button>
                  <button
                    className="mt-4 text-center flex-center ml-4 mr-4 btn btn-primary text-white border-lime-600 bg-lime-600"
                    onClick={() => setShowQRCode(!showQRCode)}
                  >
                    {showQRCode ? 'QR 닫기' : 'QR 보기'}
                  </button>
                </div>
              ) : (
                <button
                  className="btn btn-primary text-white bg-lime-500"
                  type="submit"
                >
                  로그인
                </button>
              )}
              {showEditForm && (
                <div>
                  <h2 className="text-2xl text-center text-lime-500 mt-4">
                    내 정보 수정하기
                  </h2>
                  <form className="mt-4" onSubmit={handleUpdate}>
                    <input
                      type="text"
                      name="newEmail"
                      placeholder="새로운 이메일"
                      value={formData.newEmail}
                      onChange={handleFormChange}
                      className="mt-2 p-2 border rounded"
                    />
                    <input
                      type="text"
                      name="newPassword"
                      placeholder="새로운 비밀번호"
                      value={formData.newPassword}
                      onChange={handleFormChange}
                      className="mt-2 p-2 border rounded"
                    />
                    <button
                      className="mt-4 btn btn-primary text-white bg-lime-500"
                      type="submit"
                    >
                      저장하기
                    </button>
                  </form>
                </div>
              )}
              {showQRCode && user && (
                <div className="mt-4 text-center">
                <h2 className="text-2xl text-lime-500">내 결제 정보</h2>
                <div className="flex justify-center">
                  <QRCode value={`Username: ${user.username}, Email: ${user.email}`} />
                </div>
              </div>
              )}
            </div>
            <Link to="/" className="flex justify-center text-center btn btn-link text-lime-500">
              메인 페이지로 이동하기
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyPage;
