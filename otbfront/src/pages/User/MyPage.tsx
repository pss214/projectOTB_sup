import React, { useState, useCallback, useEffect } from 'react';
import { SERVER_URL } from '../../server/getServer';
import { Link } from '../../components';
import * as U from '../../utils';
import axios from 'axios';
import type { ChangeEvent } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthProvider, useAuth } from '../../contexts';
import Logout from '../../routes/Auth/Logout';

const MyPage: React.FC = () => {
  const [user, setUser] = useState<any | null>(null);
  const [jwt, setJwt] = useState<string>('');
  const [loginData, setPassword] = useState({ password: '' });
  const [getpassword, setGetpassword] = useState<string>('');

  const {logout} = useAuth()
  useEffect(()=>{
    U.readStringP('jwt').then((jwt) => {
      setJwt(jwt ?? '');
    });
    U.readStringP('password').then((password)=>{
      setGetpassword(password ?? '')
    })
  },[jwt, getpassword])
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setPassword((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };
  type FormType = Record<'password', string>;
  const initialFormState = { password: '' };
  const changed = useCallback(
    (key: string) => (e: ChangeEvent<HTMLInputElement>) => {
      setForm((obj) => ({ ...obj, [key]: e.target.value }));
    },
    []
  );
  const [{ password }, setForm] = useState<FormType>(initialFormState);
  const deleteuser = ()=>{
    if(window.confirm("삭제하시겠습니까?")){
      axios.delete(SERVER_URL+'/member',{
        headers:{
          'Content-Type' : 'applecation/json',
          Authorization : `otb ${jwt}`
        }
      }).then(res=>{
        if(res.data.status == 201){
          alert("삭제되었습니다")
          logout(() => {
            navigate('/') // 홈 페이지로 이동합니다
          })
        }
      }).catch(error=>{
        alert("오류! 다시 입력해주세요.")
      })
    }else{

    }
  }
  const handleSubmit =  (e: React.FormEvent<HTMLFormElement>) => {
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
      .catch((error) => {
        console.error('정보를 가져오는데 실패했습니다.', error);
      });
    e.preventDefault();
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

  const navigate = useNavigate()
  const handleUpdate = (e: React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();
      axios({
        method:'POST',
        url:SERVER_URL+'/member',
        headers :{
          "Content-Type": "application/json",
          Authorization: `otb ${jwt}`
        },
        data : {
          email: formData.newEmail,
          password : formData.newPassword
        }
      }).then(res =>{
        if(res.data.status == 201){
          alert(res.data.message)
          navigate("/")
        }
      })
      .catch(error=>{
        console.log(error.data)
      })
    // 수정 정보를 서버로 전송하는 로직을 추가하세요
    // axios 또는 fetch 등을 사용하여 서버로 수정 정보를 보낼 수 있습니다.
  };

  return (
    <div>
      <div className="flex justify-between bg-lime-200">
        <div className="flex p-2 ">
          <Link to="/" className="ml-1">
            <img
              src="/img/otblogogogo.png"
              alt="OTB(우비) 로고"
              className="w-12.5 h-12.5 bg-lime-200"
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
                    className="flex-center ml-4 mr-4 btn btn-primary text-white  border-lime-600 bg-lime-600"
                    onClick={toggleEditForm}
                  >
                    수정하기
                  </button>
                  <button className="flex-center ml-4 mr-4 btn btn-primary text-white  border-lime-600 bg-lime-600"
                  onClick={deleteuser}>
                    회원탈퇴
                  </button>
                </div>
              ) : (
                <form className="text-center" onSubmit={handleSubmit}>
                  <input
                    type="password"
                    name="password"
                    placeholder="비밀번호를 입력해주세요"
                    value={loginData.password}
                    onChange={handleChange}
                  />
                  <button
                    className="btn btn-primary text-white bg-lime-500"
                    type="submit"
                  >
                    로그인
                  </button>
                </form>
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
            </div>
            <Link to="/" className="btn btn-link text-lime-500">
              메인 페이지로 이동하기
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyPage;
