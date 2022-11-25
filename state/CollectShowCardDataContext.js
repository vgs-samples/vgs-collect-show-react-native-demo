import React, {createContext, useState} from 'react';

export const CollectShowCardDataContext = createContext({
  payload: {},
  updatePayload: newPayload => {},
  clearPayload: () => {},
});

function CollectShowCardDataContextProvider({children}) {
  const [payloadData, setPayloadData] = useState({});

  function updatePayload(newPayload) {
    console.log('newPayload');
    console.log(newPayload);
    setPayloadData(currentState => newPayload);
  }

  function clearPayload() {
    setPayloadData(null);
  }

  const value = {
    payload: payloadData,
    updatePayload: updatePayload,
    clearPayload: clearPayload,
  };

  return (
    <CollectShowCardDataContext.Provider value={value}>
      {children}
    </CollectShowCardDataContext.Provider>
  );
}

export default CollectShowCardDataContextProvider;
