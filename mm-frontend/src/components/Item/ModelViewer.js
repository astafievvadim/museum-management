import React from 'react';
import { Canvas } from '@react-three/fiber';
import { OrbitControls, useGLTF } from '@react-three/drei';

function Model({ url }) {
  const { scene } = useGLTF(url);
  return <primitive object={scene} dispose={null} />;
}

export default function ModelViewer({ modelUrl }) {
  return (
    <Canvas style={{ height: 600 }}>
      <ambientLight intensity={0.5} />
      <directionalLight position={[0, 5, 5]} />
      <Model url={modelUrl} />
      <OrbitControls />
    </Canvas>
  );
}
