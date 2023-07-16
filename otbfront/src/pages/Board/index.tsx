import React, { useState, useEffect } from 'react';//useEffect로 열고 닫기 버튼 기능
import { useMemo } from 'react';
import { DragDropContext } from 'react-beautiful-dnd';
import { Title } from '../../components';
import CreateListForm from './CreateListForm';
import BoardList from '../BoardList';
import { ListDroppable } from '../../components';
import { useLists } from '../../store/useLists';
import { Button } from '../../theme/daisyui';
import Kakao from './Kakao'; // 추가: Kakao 컴포넌트를 가져옵니다.

export default function Board() {
  const { lists, onRemoveList, onCreateList, onMoveList, onDragEnd } = useLists();
  const [showMap, setShowMap] = useState(false); // 추가: 지도를 보여줄 상태 추가

  // 지도 버튼을 누를 때 호출되는 함수
  const handleToggleMap = () => {
    setShowMap((prevShowMap) => !prevShowMap);
  };

  const children = useMemo(
    () =>
      lists.map((list, index) => (
        <BoardList
          key={list.uuid}
          list={list}
          onRemoveList={onRemoveList(list.uuid)}
          index={index}
          onMoveList={onMoveList}
        />
      )),
    [lists, onRemoveList, onMoveList]
  );

  const handleShowMap = () => {
    setShowMap(true);
  };

    // 지도가 열려있을 때만 카카오지도 컴포넌트를 렌더링하도록 수정
  return (
    <section className="mt-4">
      <Title>원하시는 서비스를 선택하세요.</Title>
      <DragDropContext onDragEnd={onDragEnd}>
        <ListDroppable className="flex flex-row p-2 mt-4">
          <div className="space-x-4">
            <Button
              className="text-white border-lime-500 bg-lime-500"
              onClick={handleToggleMap}
            >
              {showMap ? '지도 닫기' : '지도 보기'}
            </Button>

            <Button className="text-white border-lime-500 bg-lime-500">
              내 주변 버스 탑승하기
            </Button>
          </div>
        </ListDroppable>
      </DragDropContext>

      {/* showMap 상태에 따라 지도를 보여줍니다. */}
      {showMap && <Kakao />}
    </section>
  );
}