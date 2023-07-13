import {useMemo} from 'react'
import {DragDropContext} from 'react-beautiful-dnd'
import {Title} from '../../components'
import CreateListForm from './CreateListForm'
import BoardList from '../BoardList'
import {ListDroppable} from '../../components'

import {useLists} from '../../store/useLists'
import { Button } from '../../theme/daisyui'

export default function Board() {
  const {lists, onRemoveList, onCreateList, onMoveList, onDragEnd} = useLists()

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
  )

  return (
    <section className="mt-4">
      <Title>원하시는 서비스를 선택하세요.</Title>
      <DragDropContext onDragEnd={onDragEnd}>
        <ListDroppable className="flex flex-row p-2 mt-4">
          <div className="space-x-4">
          <Button className="text-white border-lime-500 bg-lime-500">
            지도 보기
          </Button>

          <Button className="text-white border-lime-500 bg-lime-500">
            내 주변 버스 탑승하기
          </Button>
        
      </div>
        </ListDroppable>
      </DragDropContext>
    </section>
  )
}
