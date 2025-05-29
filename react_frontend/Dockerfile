# Используем официальный Node.js образ (лучше на базе Alpine для легковесности)
FROM node:18-alpine

# Создаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем package.json и package-lock.json (если есть) для установки зависимостей
COPY package*.json ./

# Устанавливаем зависимости
RUN npm install

# Копируем весь проект в контейнер
COPY . .

# Открываем порт 3000 (стандартный порт React development server)
EXPOSE 3000

# Запускаем приложение командой npm start
CMD ["npm", "start"]
